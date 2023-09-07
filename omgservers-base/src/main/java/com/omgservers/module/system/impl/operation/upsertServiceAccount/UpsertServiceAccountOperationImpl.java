package com.omgservers.module.system.impl.operation.upsertServiceAccount;

import com.omgservers.model.event.body.ServiceAccountCreatedEventBodyModel;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import com.omgservers.module.system.factory.LogModelFactory;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.executeChangeObject.ExecuteChangeObjectOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.SqlConnection;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneOffset;
import java.util.Arrays;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpsertServiceAccountOperationImpl implements UpsertServiceAccountOperation {

    final ExecuteChangeObjectOperation executeChangeObjectOperation;
    final LogModelFactory logModelFactory;

    @Override
    public Uni<Boolean> upsertServiceAccount(final ChangeContext<?> changeContext,
                                             final SqlConnection sqlConnection,
                                             final ServiceAccountModel serviceAccount) {
        return executeChangeObjectOperation.executeChangeObject(
                changeContext, sqlConnection, 0,
                """
                        insert into internal.tab_service_account(id, created, modified, username, password_hash)
                        values($1, $2, $3, $4, $5)
                        on conflict (id) do
                        nothing
                        """,
                Arrays.asList(
                        serviceAccount.getId(),
                        serviceAccount.getCreated().atOffset(ZoneOffset.UTC),
                        serviceAccount.getModified().atOffset(ZoneOffset.UTC),
                        serviceAccount.getUsername(),
                        serviceAccount.getPasswordHash()
                ),
                () -> new ServiceAccountCreatedEventBodyModel(serviceAccount.getId(), serviceAccount.getUsername()),
                () -> logModelFactory.create("Service account was inserted, " +
                        "serviceAccount=" + serviceAccount)
        );
    }
}
