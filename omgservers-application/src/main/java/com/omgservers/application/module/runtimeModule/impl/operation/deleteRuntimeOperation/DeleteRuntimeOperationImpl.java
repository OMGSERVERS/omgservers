package com.omgservers.application.module.runtimeModule.impl.operation.deleteRuntimeOperation;

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
class DeleteRuntimeOperationImpl implements DeleteRuntimeOperation {

    static private final String sql = """
            delete from $schema.tab_runtime where uuid = $1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> deleteRuntime(SqlConnection sqlConnection, int shard, UUID uuid) {
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
                        log.info("Runtime was deleted, shard={}, uuid={}", shard, uuid);
                    } else {
                        log.warn("Runtime was not found, skip operation, shard={}, uuid={}", shard, uuid);
                    }
                });
    }
}
