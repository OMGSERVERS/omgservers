package com.omgservers.module.runtime.impl.operation.upsertRuntime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.event.body.RuntimeCreatedEventBodyModel;
import com.omgservers.model.runtime.RuntimeModel;
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
class UpsertRuntimeOperationImpl implements UpsertRuntimeOperation {

    static private final String SQL = """
            insert into $schema.tab_runtime(id, created, modified, tenant_id, stage_id, version_id, matchmaker_id, match_id, type, current_step, config)
            values($1, $2, $3, $4, $5, $6, $7, $8, $9, $10, $11)
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
    public Uni<Boolean> upsertRuntime(final ChangeContext<?> changeContext,
                                      final SqlConnection sqlConnection,
                                      final int shard,
                                      final RuntimeModel runtime) {
        if (changeContext == null) {
            throw new ServerSideBadRequestException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (runtime == null) {
            throw new ServerSideBadRequestException("runtime is null");
        }

        return upsertObject(sqlConnection, shard, runtime)
                .call(objectWasDeleted -> upsertEvent(objectWasDeleted, changeContext, sqlConnection, runtime))
                .call(objectWasDeleted -> upsertLog(objectWasDeleted, changeContext, sqlConnection, runtime))
                .invoke(objectWasDeleted -> {
                    if (objectWasDeleted) {
                        log.info("Runtime was inserted, shard={}, runtime={}", shard, runtime);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(final SqlConnection sqlConnection,
                              final int shard,
                              final RuntimeModel runtime) {
        try {
            var preparedSql = prepareShardSqlOperation.prepareShardSql(SQL, shard);
            var configString = objectMapper.writeValueAsString(runtime.getConfig());
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(Arrays.asList(
                            runtime.getId(),
                            runtime.getCreated().atOffset(ZoneOffset.UTC),
                            runtime.getModified().atOffset(ZoneOffset.UTC),
                            runtime.getTenantId(),
                            runtime.getStageId(),
                            runtime.getVersionId(),
                            runtime.getMatchmakerId(),
                            runtime.getMatchId(),
                            runtime.getType(),
                            runtime.getCurrentStep(),
                            configString
                    )))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }

    Uni<Boolean> upsertEvent(final boolean objectWasDeleted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final RuntimeModel runtime) {
        if (objectWasDeleted) {
            final var body = new RuntimeCreatedEventBodyModel(
                    runtime.getId(),
                    runtime.getMatchmakerId(),
                    runtime.getMatchId());
            final var event = eventModelFactory.create(body);
            return upsertEventOperation.upsertEvent(changeContext, sqlConnection, event);
        } else {
            return Uni.createFrom().item(false);
        }
    }

    Uni<Boolean> upsertLog(final boolean objectWasDeleted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final RuntimeModel runtime) {
        if (objectWasDeleted) {
            final var changeLog = logModelFactory.create("Runtime was inserted, runtime=" + runtime);
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
