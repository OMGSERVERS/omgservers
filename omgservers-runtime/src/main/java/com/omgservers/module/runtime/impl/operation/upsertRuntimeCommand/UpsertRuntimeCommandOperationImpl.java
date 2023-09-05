package com.omgservers.module.runtime.impl.operation.upsertRuntimeCommand;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideInternalException;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
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
class UpsertRuntimeCommandOperationImpl implements UpsertRuntimeCommandOperation {

    static private final String sql = """
            insert into $schema.tab_runtime_command(id, runtime_id, created, modified, qualifier, body, status, step)
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
    public Uni<Boolean> upsertRuntimeCommand(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final int shard,
                                             final RuntimeCommandModel runtimeCommand) {
        if (changeContext == null) {
            throw new ServerSideBadRequestException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (runtimeCommand == null) {
            throw new ServerSideBadRequestException("runtimeCommand is null");
        }

        return upsertObject(sqlConnection, shard, runtimeCommand)
                .call(objectWasInserted -> upsertEvent(objectWasInserted, changeContext, sqlConnection, runtimeCommand))
                .call(objectWasInserted -> upsertLog(objectWasInserted, changeContext, sqlConnection, runtimeCommand))
                .invoke(objectWasInserted -> {
                    if (objectWasInserted) {
                        log.info("Runtime command was inserted, shard={}, runtimeCommand={}", shard, runtimeCommand);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertObject(final SqlConnection sqlConnection,
                              final int shard,
                              final RuntimeCommandModel runtimeCommand) {
        try {
            var bodyString = objectMapper.writeValueAsString(runtimeCommand.getBody());
            var preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
            return sqlConnection.preparedQuery(preparedSql)
                    .execute(Tuple.from(Arrays.asList(
                            runtimeCommand.getId(),
                            runtimeCommand.getRuntimeId(),
                            runtimeCommand.getCreated().atOffset(ZoneOffset.UTC),
                            runtimeCommand.getModified().atOffset(ZoneOffset.UTC),
                            runtimeCommand.getQualifier(),
                            bodyString,
                            runtimeCommand.getStatus(),
                            runtimeCommand.getStep()
                    )))
                    .map(rowSet -> rowSet.rowCount() > 0);
        } catch (IOException e) {
            throw new ServerSideInternalException(e.getMessage(), e);
        }
    }

    Uni<Boolean> upsertEvent(final boolean objectWasDeleted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final RuntimeCommandModel runtimeCommand) {
        return Uni.createFrom().item(false);
    }

    Uni<Boolean> upsertLog(final boolean objectWasDeleted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final RuntimeCommandModel runtimeCommand) {
        if (objectWasDeleted) {
            final var changeLog = logModelFactory.create("Runtime command was inserted, " +
                    "runtimeCommand=" + runtimeCommand);
            return upsertLogOperation.upsertLog(sqlConnection, changeLog)
                    .invoke(logWasInserted -> {
                        if (logWasInserted) {
                            changeContext.add(changeLog);
                        }
                    });
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
