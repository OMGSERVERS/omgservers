package com.omgservers.module.internal.impl.operation.deleteServiceAccount;

import com.omgservers.exception.ServerSideBadRequestException;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
class DeleteServiceAccountOperationImpl implements DeleteServiceAccountOperation {

    static private final String sql = """
            delete from internal.tab_service_account where id = $1
            """;

    @Override
    public Uni<Void> deleteServiceAccount(final SqlConnection sqlConnection, final Long id) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (id == null) {
            throw new ServerSideBadRequestException("id is null");
        }

        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(id))
                .invoke(rowSet -> log.info("Service account was deleted, uuid={}", id))
                .replaceWithVoid();
    }
}
