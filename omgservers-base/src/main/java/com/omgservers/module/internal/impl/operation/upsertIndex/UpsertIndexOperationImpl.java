package com.omgservers.module.internal.impl.operation.upsertIndex;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.index.IndexModel;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.pgclient.PgException;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.ZoneOffset;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertIndexOperationImpl implements UpsertIndexOperation {

    static private final String sql = """
            insert into internal.tab_index(id, created, modified, name, version, config)
            values($1, $2, $3, $4, $5, $6)
            on conflict (id) do
            nothing
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertIndex(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final IndexModel index) {
        if (changeContext == null) {
            throw new IllegalArgumentException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (index == null) {
            throw new ServerSideBadRequestException("index is null");
        }

        return upsertObject(sqlConnection, index)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, index))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, index))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Index was inserted, index={}", index);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(SqlConnection sqlConnection, IndexModel index) {
        try {
            String configString = objectMapper.writeValueAsString(index.getConfig());
            return sqlConnection.preparedQuery(sql)
                    .execute(Tuple.of(index.getId(),
                            index.getCreated().atOffset(ZoneOffset.UTC),
                            index.getModified().atOffset(ZoneOffset.UTC),
                            index.getName(),
                            index.getVersion(),
                            configString))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final IndexModel index) {
        return Uni.createFrom().item(false);
    }

    Uni<Boolean> upsertLog(final boolean objectWasInserted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final IndexModel index) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create("Index was inserted, index=" + index);
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
