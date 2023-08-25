package com.omgservers.base.impl.operation.upsertServiceAccountOperation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import io.vertx.mutiny.sqlclient.Tuple;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertServiceAccountOperationImpl implements UpsertServiceAccountOperation {

    static private final String sql = """
            insert into internal.tab_service_account(id, created, modified, username, password_hash)
            values($1, $2, $3, $4, $5)
            on conflict (id) do
            update set modified = $3, password_hash = $5
            """;

    final ObjectMapper objectMapper;

    @Override
    public Uni<Void> upsertServiceAccount(final SqlConnection sqlConnection, final ServiceAccountModel serviceAccount) {
        if (sqlConnection == null) {
            throw new ServerSideBadRequestException("sqlConnection is null");
        }
        if (serviceAccount == null) {
            throw new ServerSideBadRequestException("serviceAccount is null");
        }

        return upsertQuery(sqlConnection, serviceAccount)
                .invoke(voidItem -> log.info("Service account was synchronized, serviceAccount={}", serviceAccount));
    }

    Uni<Void> upsertQuery(SqlConnection sqlConnection, ServiceAccountModel serviceAccount) {
        return sqlConnection.preparedQuery(sql)
                .execute(Tuple.of(serviceAccount.getId(),
                        serviceAccount.getCreated().atOffset(ZoneOffset.UTC),
                        serviceAccount.getModified().atOffset(ZoneOffset.UTC),
                        serviceAccount.getUsername(),
                        serviceAccount.getPasswordHash()))
                .replaceWithVoid();
    }
}
