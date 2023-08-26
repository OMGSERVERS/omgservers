package com.omgservers.application.module.serviceAccountModule.serviceAccountWebService.impl.operation;

import com.omgservers.base.module.internal.impl.operation.deleteServiceAccount.DeleteServiceAccountOperation;
import com.omgservers.base.module.internal.impl.operation.selectServiceAccount.SelectServiceAccountOperation;
import com.omgservers.base.module.internal.impl.operation.upsertServiceAccount.UpsertServiceAccountOperation;
import com.omgservers.model.serviceAccount.ServiceAccountModel;
import com.omgservers.exception.ServerSideNotFoundException;
import com.omgservers.base.factory.ServiceAccountModelFactory;
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
    ServiceAccountModelFactory serviceAccountModelFactory;

    @Inject
    PgPool pgPool;

    @Test
    void whenRegularUsage_thenOk() {
        final var username = UUID.randomUUID().toString();
        final var serviceAccount = serviceAccountModelFactory.create(username, "passwordhash");

        syncServiceAccount(serviceAccount);
        final var serviceAccount1 = getServiceAccount(username);
        assertEquals(serviceAccount, serviceAccount1);

        final var newPasswordHash = "newpasswordhash";
        serviceAccount.setPasswordHash(newPasswordHash);
        syncServiceAccount(serviceAccount);

        final var serviceAccount2 = getServiceAccount(username);
        assertEquals(newPasswordHash, serviceAccount2.getPasswordHash());

        deleteServiceAccount(serviceAccount.getId());

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

    void deleteServiceAccount(Long id) {
        pgPool.withTransaction(sqlConnection -> deleteServiceAccountOperation
                        .deleteServiceAccount(sqlConnection, id))
                .await().atMost(Duration.ofSeconds(1));
    }
}
