package com.omgservers.module.matchmaker.impl.operation.deleteMatch;

import com.omgservers.operation.prepareShardSql.PrepareShardSqlOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteMatchOperationImpl implements DeleteMatchOperation {

    static private final String sql = """
            delete from $schema.tab_matchmaker_match where id = $1
            """;

    final PrepareShardSqlOperation prepareShardSqlOperation;

    @Override
    public Uni<Boolean> deleteMatch(SqlConnection sqlConnection, int shard, Long id) {
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
                        log.info("Match was deleted, shard={}, id={}", shard, id);
                    } else {
                        log.warn("Match was not found, skip operation, shard={}, id={}", shard, id);
                    }
                });
    }
}