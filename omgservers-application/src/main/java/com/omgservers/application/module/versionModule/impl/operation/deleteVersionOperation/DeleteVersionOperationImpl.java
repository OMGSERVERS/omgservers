package com.omgservers.application.module.versionModule.impl.operation.deleteVersionOperation;

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
class DeleteVersionOperationImpl implements DeleteVersionOperation {

    static private final String sql = """
            delete from $schema.tab_version where id = $1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> deleteVersion(final SqlConnection sqlConnection,
                                      final int shard,
                                      final Long id) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (id == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        String preparedSql = prepareShardSqlOperation.prepareShardSql(sql, shard);

        return sqlConnection.preparedQuery(preparedSql)
                .execute(Tuple.of(id))
                .map(rowSet -> rowSet.rowCount() > 0)
                .invoke(deleted -> {
                    if (deleted) {
                        log.info("Version was deleted, shard={}, id={}", shard, id);
                    } else {
                        log.info("Version was not found, skip operation, shard={}, id={}", shard, id);
                    }
                });
    }
}
