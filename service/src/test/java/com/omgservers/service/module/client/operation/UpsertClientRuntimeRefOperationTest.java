package com.omgservers.service.module.client.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.client.ClientModelFactory;
import com.omgservers.service.factory.client.ClientRuntimeRefModelFactory;
import com.omgservers.service.module.client.operation.testInterface.UpsertClientOperationTestInterface;
import com.omgservers.service.module.client.operation.testInterface.UpsertClientRuntimeRefOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertClientRuntimeRefOperationTest extends Assertions {

    @Inject
    UpsertClientOperationTestInterface upsertClientOperation;

    @Inject
    UpsertClientRuntimeRefOperationTestInterface upsertClientRuntimeRefOperation;

    @Inject
    ClientModelFactory clientModelFactory;

    @Inject
    ClientRuntimeRefModelFactory clientRuntimeRefModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenClientRuntimeRef_whenUpsertClientRuntimeRef_thenInserted() {
        final var shard = 0;
        final var client = clientModelFactory.create(userId(), playerId(), tenantId(), versionId());
        upsertClientOperation.upsertClient(shard, client);

        final var clientRuntimeRef = clientRuntimeRefModelFactory.create(client.getId(), runtimeId());
        final var changeContext = upsertClientRuntimeRefOperation.upsertClientRuntimeRef(shard, clientRuntimeRef);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.CLIENT_RUNTIME_REF_CREATED));
    }

    @Test
    void givenClientRuntimeRef_whenUpsertClientRuntimeRef_thenUpdated() {
        final var shard = 0;
        final var client = clientModelFactory.create(userId(), playerId(), tenantId(), versionId());
        upsertClientOperation.upsertClient(shard, client);

        final var clientRuntimeRef = clientRuntimeRefModelFactory.create(client.getId(), runtimeId());
        upsertClientRuntimeRefOperation.upsertClientRuntimeRef(shard, clientRuntimeRef);

        final var changeContext = upsertClientRuntimeRefOperation.upsertClientRuntimeRef(shard, clientRuntimeRef);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.CLIENT_RUNTIME_REF_CREATED));
    }

    @Test
    void givenUnknownId_whenUpsertClientRuntimeRef_thenException() {
        final var shard = 0;
        final var clientRuntimeRef = clientRuntimeRefModelFactory.create(clientId(), runtimeId());
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertClientRuntimeRefOperation.upsertClientRuntimeRef(shard, clientRuntimeRef));
    }

    @Test
    void givenClientRuntimeRef_whenUpsertClientRuntimeRef_thenIdempotencyViolation() {
        final var shard = 0;
        final var client = clientModelFactory.create(userId(), playerId(), tenantId(), versionId());
        upsertClientOperation.upsertClient(shard, client);

        final var clientRuntimeRef1 = clientRuntimeRefModelFactory.create(client.getId(), runtimeId());
        upsertClientRuntimeRefOperation.upsertClientRuntimeRef(shard, clientRuntimeRef1);

        final var clientRuntimeRef2 = clientRuntimeRefModelFactory.create(client.getId(),
                runtimeId(),
                clientRuntimeRef1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertClientRuntimeRefOperation.upsertClientRuntimeRef(shard, clientRuntimeRef2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long userId() {
        return generateIdOperation.generateId();
    }

    Long playerId() {
        return generateIdOperation.generateId();
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long clientId() {
        return generateIdOperation.generateId();
    }

    Long runtimeId() {
        return generateIdOperation.generateId();
    }
}