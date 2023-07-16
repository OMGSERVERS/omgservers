package com.omgservers.application.module.userModule.impl.operation.deletePlayerOperation;

import com.omgservers.application.operation.prepareShardSqlOperation.PrepareShardSqlOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeletePlayerOperationImpl implements DeletePlayerOperation {

    static private final String sql = """
            delete from $schema.tab_user_player where uuid = $1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> deletePlayer(final SqlConnection sqlConnection,
                                     final int shard,
                                     final UUID uuid) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (uuid == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(uuid))
                .map(rowSet -> rowSet.rowCount() > 0)
                .invoke(deleted -> {
                    if (deleted) {
                        log.info("Player was deleted, shard={}, uuid={}", shard, uuid);
                    } else {
                        log.warn("Player was not found, skip operation, shard={}, uuid={}", shard, uuid);
                    }
                });
    }
}
