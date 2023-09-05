package com.omgservers.module.tenant.impl.operation.deleteStage;

import com.omgservers.ChangeContext;
import com.omgservers.model.event.body.StageDeletedEventBodyModel;
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

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteStageOperationImpl implements DeleteStageOperation {

    static private final String sql = """
            delete from $schema.tab_tenant_stage
            where id = $1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteStage(final ChangeContext<?> changeContext,
                                    final SqlConnection sqlConnection,
                                    final int shard,
                                    final Long tenantId,
                                    final Long id) {
        if (changeContext == null) {
            throw new IllegalArgumentException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (tenantId == null) {
            throw new IllegalArgumentException("tenantId is null");
        }
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(id))
                .map(rowSet -> rowSet.rowCount() > 0)
                .call(objectWasDeleted -> upsertEvent(objectWasDeleted, changeContext, sqlConnection, tenantId, id))
                .call(objectWasDeleted -> upsertLog(objectWasDeleted, changeContext, sqlConnection, tenantId, id))
                .invoke(objectWasDeleted -> {
                    if (objectWasDeleted) {
                        log.info("Stage was deleted, shard={}, id={}", shard, id);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertEvent(final boolean objectWasDeleted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final Long tenantId,
                             final Long id) {
        if (objectWasDeleted) {
            final var body = new StageDeletedEventBodyModel(tenantId, id);
            final var event = eventModelFactory.create(body);
            return upsertEventOperation.upsertEvent(sqlConnection, event)
                    .invoke(eventWasInserted -> {
                        if (eventWasInserted) {
                            changeContext.add(event);
                        }
                    });
        } else {
            return Uni.createFrom().item(false);
        }
    }

    Uni<Boolean> upsertLog(final boolean objectWasDeleted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final Long tenantId,
                           final Long id) {
        if (objectWasDeleted) {
            final var changeLog = logModelFactory.create(String.format("Stage was deleted, " +
                    "tenantId=%d, id=%d", tenantId, id));
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
