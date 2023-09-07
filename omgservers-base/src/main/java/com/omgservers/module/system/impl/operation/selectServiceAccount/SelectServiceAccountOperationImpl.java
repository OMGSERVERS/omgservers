package com.omgservers.module.system.impl.operation.selectServiceAccount;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import com.omgservers.operation.executeSelectObject.ExecuteSelectObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SelectServiceAccountOperationImpl implements SelectServiceAccountOperation {

    final ExecuteSelectObjectOperation executeSelectObjectOperation;

    final ObjectMapper objectMapper;

    @Override
    public Uni<ServiceAccountModel> selectServiceAccount(final SqlConnection sqlConnection, final String username) {
        return executeSelectObjectOperation.executeSelectObject(
                sqlConnection,
                0,
                """
                        select id, created, modified, username, password_hash
                        from internal.tab_service_account
                        where username = $1
                        limit 1
                        """,
                Arrays.asList(username),
                "Service account",
                this::createServiceAccount);
    }

    ServiceAccountModel createServiceAccount(Row row) {
        ServiceAccountModel serviceAccount = new ServiceAccountModel();
        serviceAccount.setId(row.getLong("id"));
        serviceAccount.setCreated(row.getOffsetDateTime("created").toInstant());
        serviceAccount.setModified(row.getOffsetDateTime("modified").toInstant());
        serviceAccount.setUsername(row.getString("username"));
        serviceAccount.setPasswordHash(row.getString("password_hash"));
        return serviceAccount;
    }
}
