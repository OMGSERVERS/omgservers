package com.omgservers.module.runtime.impl.operation.updateRuntimeCurrentStep;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.exception.ServerSideNotFoundException;
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

import java.time.Instant;
import java.time.ZoneOffset;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateRuntimeCurrentStepOperationImpl implements UpdateRuntimeCurrentStepOperation {

    static private final String sql = """
            update $schema.tab_runtime
            set modified = $2, current_step = $3
            where id = $1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Boolean> updateRuntimeCurrentStep(final ChangeContext<?> changeContext,
                                                 final SqlConnection sqlConnection,
                                                 final int shard,
                                                 final Long id,
                                                 final Long currentStep) {
        if (changeContext == null) {
            throw new ServerSideBadRequestException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (id == null) {
            throw new ServerSideBadRequestException("id is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(
                        id,
                        Instant.now().atOffset(ZoneOffset.UTC),
                        currentStep))
                .map(rowSet -> rowSet.rowCount() > 0)
                .call(objectWasUpdated -> upsertEvent(objectWasUpdated, changeContext, sqlConnection, id))
                .call(objectWasUpdated -> upsertLog(objectWasUpdated, changeContext, sqlConnection, id))
                .invoke(objectWasUpdated -> {
                    if (objectWasUpdated) {
                        log.info("Current step of runtime was updated, " +
                                "shard={}, id={}, newValue={}", shard, id, currentStep);
                    } else {
                        throw new ServerSideNotFoundException("runtime was not found, id=" + id);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertEvent(final boolean objectWasUpdated,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final Long id) {
        return Uni.createFrom().item(false);
    }

    Uni<Boolean> upsertLog(final boolean objectWasUpdated,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final Long id) {
        return Uni.createFrom().item(false);
    }
}
