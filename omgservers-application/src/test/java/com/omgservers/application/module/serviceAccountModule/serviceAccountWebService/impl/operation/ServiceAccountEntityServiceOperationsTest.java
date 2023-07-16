package com.omgservers.application.module.serviceAccountModule.serviceAccountWebService.impl.operation;

import com.omgservers.application.module.internalModule.impl.operation.deleteServiceAccountOperation.DeleteServiceAccountOperation;
import com.omgservers.application.module.internalModule.impl.operation.selectServiceAccountOperation.SelectServiceAccountOperation;
import com.omgservers.application.module.internalModule.impl.operation.upsertServiceAccountOperation.UpsertServiceAccountOperation;
import com.omgservers.application.module.internalModule.model.serviceAccount.ServiceAccountModel;
import com.omgservers.application.exception.ServerSideNotFoundException;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@QuarkusTest
public class ServiceAccountEntityServiceOperationsTest extends Assertions {

    @Inject
    SelectServiceAccountOperation getServiceAccountOperation;

    @Inject
    UpsertServiceAccountOperation upsertServiceAccountOperation;

    @Inject
    DeleteServiceAccountOperation deleteServiceAccountOperation;

    @Inject
    PgPool pgPool;

    @Test
    void whenRegularUsage_thenOk() {
        final var username = UUID.randomUUID().toString();
        final var serviceAccount = ServiceAccountModel.create(username, "passwordhash");

        syncServiceAccount(serviceAccount);
        final var serviceAccount1 = getServiceAccount(username);
        assertEquals(serviceAccount, serviceAccount1);

        final var newPasswordHash = "newpasswordhash";
        serviceAccount.setPasswordHash(newPasswordHash);
        syncServiceAccount(serviceAccount);

        final var serviceAccount2 = getServiceAccount(username);
        assertEquals(newPasswordHash, serviceAccount2.getPasswordHash());

        deleteServiceAccount(serviceAccount.getUuid());

        assertThrows(ServerSideNotFoundException.class, () -> getServiceAccount(username));
    }

    ServiceAccountModel getServiceAccount(String username) {
        return pgPool.withTransaction(sqlConnection -> getServiceAccountOperation
                        .selectServiceAccount(sqlConnection, username))
                .await().atMost(Duration.ofSeconds(1));
    }

    void syncServiceAccount(ServiceAccountModel serviceAccount) {
        pgPool.withTransaction(sqlConnection -> upsertServiceAccountOperation
                        .upsertServiceAccount(sqlConnection, serviceAccount))
                .await().atMost(Duration.ofSeconds(1));
    }

    void deleteServiceAccount(UUID uuid) {
        pgPool.withTransaction(sqlConnection -> deleteServiceAccountOperation
                        .deleteServiceAccount(sqlConnection, uuid))
                .await().atMost(Duration.ofSeconds(1));
    }
}
