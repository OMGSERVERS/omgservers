package com.omgservers.application.module.userModule.impl.operation.deleteAttributeOperation;

import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteAttributeOperationImpl implements DeleteAttributeOperation {

    static private final String sql = """
            delete from $schema.tab_player_attribute
            where player_uuid = $1 and attribute_name = $2
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> deleteAttribute(final SqlConnection sqlConnection,
                                        final int shard,
                                        final UUID player,
                                        final String name) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (player == null) {
            throw new IllegalArgumentException("player is null");
        }
        if (name == null) {
            throw new IllegalArgumentException("name is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(player, name))
                .map(rowSet -> rowSet.rowCount() > 0)
                .invoke(deleted -> {
                    if (deleted) {
                        log.info("Attribute was deleted, shard={}, player={}, name={}", shard, player, name);
                    } else {
                        log.warn("Attribute or player was not found, skip operation, " +
                                "shard={}, player={}, name={}", shard, player, name);
                    }
                });
    }
}
