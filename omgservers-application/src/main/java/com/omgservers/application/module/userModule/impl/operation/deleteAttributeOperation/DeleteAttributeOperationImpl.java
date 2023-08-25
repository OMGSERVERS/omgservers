package com.omgservers.application.module.userModule.impl.operation.deleteAttributeOperation;

import com.omgservers.base.impl.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
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

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> deleteAttribute(final SqlConnection sqlConnection,
                                        final int shard,
                                        final Long playerId,
                                        final String name) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
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
                .invoke(deleted -> {
                    if (deleted) {
                        log.info("Attribute was deleted, shard={}, playerId={}, name={}", shard, playerId, name);
                    } else {
                        log.warn("Attribute or player was not found, skip operation, " +
                                "shard={}, playerId={}, name={}", shard, playerId, name);
                    }
                });
    }
}
