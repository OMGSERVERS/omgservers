package com.omgservers.base.module.internal.impl.operation.deleteIndex;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class DeleteIndexOperationImpl implements DeleteIndexOperation {

    static private final String sql = """
            delete from internal.tab_index where id = $1
            """;

    @Override
    public Uni<Void> deleteIndex(final SqlConnection sqlConnection, final Long id) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (id == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(id))
                .invoke(rowSet -> log.info("Index was deleted, uuid={}", id))
                .replaceWithVoid();
    }
}
