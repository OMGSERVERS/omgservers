package com.omgservers.application.module.internalModule.impl.operation.deleteServiceAccountOperation;

import com.omgservers.application.exception.ServerSideBadRequestException;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
class DeleteServiceAccountOperationImpl implements DeleteServiceAccountOperation {

    static private final String sql = """
            delete from internal.tab_service_account where uuid = $1
            """;

    @Override
    public Uni<Void> deleteServiceAccount(final SqlConnection sqlConnection, final UUID uuid) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (uuid == null) {
            throw new ServerSideBadRequestException("uuid is null");
        }

        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(uuid))
                .invoke(rowSet -> log.info("Service account was deleted, uuid={}", uuid))
                .replaceWithVoid();
    }
}
