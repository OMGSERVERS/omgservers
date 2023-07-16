package com.omgservers.application.module.userModule.impl.operation.deleteClientOperation;

import com.omgservers.application.exception.ServerSideBadRequestException;
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
class DeleteClientOperationImpl implements DeleteClientOperation {

    static private final String sql = """
            delete from $schema.tab_player_client where uuid = $1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> deleteClient(final SqlConnection sqlConnection,
                                     final int shard,
                                     final UUID uuid) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (uuid == null) {
            throw new ServerSideBadRequestException("uuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);
        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(uuid))
                .map(rowSet -> rowSet.rowCount() > 0)
                .invoke(deleted -> {
                    if (deleted) {
                        log.info("Client was deleted, shard={}, uuid={}", shard, uuid);
                    } else {
                        log.warn("Client was not found, skip operation, shard={}, uuid={}", shard, uuid);
                    }
                });
    }
}
