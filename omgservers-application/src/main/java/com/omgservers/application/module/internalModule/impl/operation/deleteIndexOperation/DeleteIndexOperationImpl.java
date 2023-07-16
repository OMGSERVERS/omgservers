package com.omgservers.application.module.internalModule.impl.operation.deleteIndexOperation;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
class DeleteIndexOperationImpl implements DeleteIndexOperation {

    static private final String sql = """
            delete from internal.tab_index where uuid = $1
            """;

    @Override
    public Uni<Void> deleteIndex(final SqlConnection sqlConnection, final UUID uuid) {
        if (sqlConnection == null) {
            throw new IllegalArgumentException("sqlConnection is null");
        }
        if (uuid == null) {
            throw new IllegalArgumentException("uuid is null");
        }

        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(uuid))
                .invoke(rowSet -> log.info("Index was deleted, uuid={}", uuid))
                .replaceWithVoid();
    }
}
