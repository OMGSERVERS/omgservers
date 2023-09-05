package com.omgservers.module.user.impl.operation.deleteAttribute;

import com.omgservers.ChangeContext;
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
class DeleteAttributeOperationImpl implements DeleteAttributeOperation {

    static private final String sql = """
            delete from $schema.tab_user_attribute
            where player_id = $1 and attribute_name = $2
            """;

    final TransformPgExceptionOperation transformPgExceptionOperation;
    final PrepareShardSqlOperation prepareShardSqlOperation;
    final UpsertEventOperation upsertEventOperation;
    final UpsertLogOperation upsertLogOperation;

    final EventModelFactory eventModelFactory;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> deleteAttribute(final ChangeContext<?> changeContext,
                                        final SqlConnection sqlConnection,
                                        final int shard,
                                        final Long userId,
                                        final Long playerId,
                                        final String name) {
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
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(playerId, name))
                .map(rowSet -> rowSet.rowCount() > 0)
                .call(objectWasDeleted -> upsertEvent(objectWasDeleted, changeContext, sqlConnection, userId, playerId, name))
                .call(objectWasDeleted -> upsertLog(objectWasDeleted, changeContext, sqlConnection, userId, playerId, name))
                .invoke(objectWasDeleted -> {
                    if (objectWasDeleted) {
                        log.info("Attribute was deleted, shard={}, userId={}, playerId={}, name={}", shard, userId, playerId, name);
                    }
                });
    }

    Uni<Boolean> upsertEvent(final boolean objectWasDeleted,
                             final ChangeContext<?> changeContext,
                             final SqlConnection sqlConnection,
                             final Long userId,
                             final Long playerId,
                             final String name) {
        return Uni.createFrom().item(false);
    }

    Uni<Boolean> upsertLog(final boolean objectWasDeleted,
                           final ChangeContext<?> changeContext,
                           final SqlConnection sqlConnection,
                           final Long userId,
                           final Long playerId,
                           final String name) {
        if (objectWasDeleted) {
            final var changeLog = logModelFactory.create(String.format("Attribute was deleted, " +
                    "userId=%d, playerId=%d, name=%s", userId, playerId, name));
            return upsertLogOperation.upsertLog(changeContext, sqlConnection, changeLog);
        } else {
            return Uni.createFrom().item(false);
        }
    }
}
