package com.omgservers.module.matchmaker.impl.operation.upsertRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.request.RequestModel;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
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
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertRequestOperationImpl implements UpsertRequestOperation {

    static private final String sql = """
            insert into $schema.tab_matchmaker_request(id, matchmaker_id, created, modified, user_id, client_id, mode, config)
            values($1, $2, $3, $4, $5, $6, $7, $8)
            on conflict (id) do
            nothing
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;
    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> upsertRequest(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final RequestModel request) {
        if (changeContext == null) {
            throw new ServerSideBadRequestException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (request == null) {
            throw new ServerSideBadRequestException("request is null");
        }

        return upsertObject(sqlConnection, shard, request)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, request))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, request))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Request was inserted, shard={}, request={}", shard, request);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(final SqlConnection sqlConnection,
                              final int shard,
                              final RequestModel request) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            var configString = objectMapper.writeValueAsString(request.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(Arrays.asList(
                            request.getId(),
                            request.getMatchmakerId(),
                            request.getCreated().atOffset(ZoneOffset.UTC),
                            request.getModified().atOffset(ZoneOffset.UTC),
                            request.getUserId(),
                            request.getClientId(),
                            request.getMode(),
                            configString
                    )))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final RequestModel request) {
        return Uni.createFrom().item(false);
    }

    Uni<Boolean> upsertLog(final boolean objectWasInserted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final RequestModel request) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create("Request was inserted, request=" + request);
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
