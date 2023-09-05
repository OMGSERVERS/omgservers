package com.omgservers.module.user.impl.operation.deleteObject;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.module.internal.factory.EventModelFactory;
import com.omgservers.module.internal.factory.LogModelFactory;
import com.omgservers.module.internal.impl.operation.upsertEvent.UpsertEventOperation;
import com.omgservers.module.internal.impl.operation.upsertLog.UpsertLogOperation;
import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import com.omgservers.operation.transformPgException.TransformPgExceptionOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteObjectOperationImpl implements DeleteObjectOperation {

    static private final String SQL = """
            delete from $schema.tab_user_object where id = $1
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteObject(final ChangeContext<?> changeContext,
                                     final SqlConnection sqlConnection,
                                     final int shard,
                                     final Long userId,
                                     final Long playerId,
                                     final Long id) {
        if (changeContext == null) {
            throw new IllegalArgumentException("changeContext is null");
        }
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("userId is null");
        }
        if (playerId == null) {
            throw new IllegalArgumentException("playerId is null");
        }
        if (id == null) {
            throw new IllegalArgumentException("id is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(SQL, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(id))
                .map(rowSet -> rowSet.rowCount() > 0)
                .call(objectWasDeleted -> upsertEvent(objectWasDeleted, changeContext, sqlConnection, userId, playerId, id))
                .call(objectWasDeleted -> upsertLog(objectWasDeleted, changeContext, sqlConnection, userId, playerId, id))
                .invoke(objectWasDeleted -> {
                    if (objectWasDeleted) {
                        log.info("Object was deleted, " +
                                "shard={}, userId={}, playerId={} id={}", shard, userId, playerId, id);
                    }
                });
    }

    Uni<Boolean> upsertEvent(final boolean objectWasDeleted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final Long userId,
                             final Long playerId,
                             final Long id) {
        return Uni.createFrom().item(false);
    }

    Uni<Boolean> upsertLog(final boolean objectWasDeleted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final Long userId,
                           final Long playerId,
                           final Long id) {
        if (objectWasDeleted) {
            final var changeLog = logModelFactory.create(String.format("Object was deleted, " +
                    "userId=%d, playerId=%d, id=%d", userId, playerId, id));
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
