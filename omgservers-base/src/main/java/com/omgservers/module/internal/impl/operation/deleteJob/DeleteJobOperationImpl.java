package com.omgservers.module.internal.impl.operation.deleteJob;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.event.body.JobDeletedEventBodyModel;
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

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteJobOperationImpl implements DeleteJobOperation {

    private static final String SQL = """
            delete from internal.tab_job where shard_key = $1 and entity = $2
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteJob(final ChangeContext<?> changeContext,
                                  final SqlConnection sqlConnection,
                                  final Long shardKey,
                                  final Long entity) {
        if (changeContext == null) {
            throw new IllegalArgumentException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (shardKey == null) {
            throw new ServerSideBadRequestException("shardKey is null");
        }
        if (entity == null) {
            throw new ServerSideBadRequestException("entity is null");
        }

        return sqlConnection.preparedQuery(SQL)
                .execute(Tuple.of(shardKey, entity))
                .map(rowSet -> rowSet.rowCount() > 0)
                .call(objectWasDeleted -> upsertEvent(objectWasDeleted, changeContext, sqlConnection, shardKey, entity))
                .call(objectWasDeleted -> upsertLog(objectWasDeleted, changeContext, sqlConnection, shardKey, entity))
                .invoke(objectWasDeleted -> {
                    if (objectWasDeleted) {
                        log.info("Job was deleted, shardKey={}, entity={}", shardKey, entity);
                    } else {
                        log.warn("Job was not found, skip operation, shardKey={}, entity={}", shardKey, entity);
                    }
                })
                .onFailure(PgException.class)
                .transform(t -> transformPgExceptionOperation.transformPgException((PgException) t));
    }

    Uni<Boolean> upsertEvent(final boolean objectWasInserted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final Long shardKey,
                             final Long entity) {
        if (objectWasInserted) {
            final var body = new JobDeletedEventBodyModel(shardKey, entity);
            final var event = eventModelFactory.create(body);
            return upsertEventOperation.upsertEvent(changeContext, sqlConnection, event);
        } else {
            return Uni.createFrom().item(false);
        }
    }

    Uni<Boolean> upsertLog(final boolean objectWasInserted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final Long shardKey,
                           final Long entity) {
        if (objectWasInserted) {
            final var changeLog = logModelFactory.create(String.format("Job was deleted, " +
                    "shardKey=%s, entity=%s", shardKey, entity));
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
