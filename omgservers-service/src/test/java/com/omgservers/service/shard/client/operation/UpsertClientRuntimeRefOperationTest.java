package com.omgservers.service.shard.client.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.client.ClientConfigDto;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.client.ClientModelFactory;
import com.omgservers.service.factory.client.ClientRuntimeRefModelFactory;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.client.operation.testInterface.UpsertClientOperationTestInterface;
import com.omgservers.service.shard.client.operation.testInterface.UpsertClientRuntimeRefOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertClientRuntimeRefOperationTest extends BaseTestClass {

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
        final var slot = 0;
        final var client = clientModelFactory.create(userId(), playerId(), versionId(), ClientConfigDto.create());
        upsertClientOperation.upsertClient(slot, client);

        final var clientRuntimeRef = clientRuntimeRefModelFactory.create(client.getId(), runtimeId());
        final var changeContext = upsertClientRuntimeRefOperation.upsertClientRuntimeRef(slot, clientRuntimeRef);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.CLIENT_RUNTIME_REF_CREATED));
    }

    @Test
    void givenClientRuntimeRef_whenUpsertClientRuntimeRef_thenUpdated() {
        final var slot = 0;
        final var client = clientModelFactory.create(userId(), playerId(), versionId(), ClientConfigDto.create());
        upsertClientOperation.upsertClient(slot, client);

        final var clientRuntimeRef = clientRuntimeRefModelFactory.create(client.getId(), runtimeId());
        upsertClientRuntimeRefOperation.upsertClientRuntimeRef(slot, clientRuntimeRef);

        final var changeContext = upsertClientRuntimeRefOperation.upsertClientRuntimeRef(slot, clientRuntimeRef);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.CLIENT_RUNTIME_REF_CREATED));
    }

    @Test
    void givenUnknownId_whenUpsertClientRuntimeRef_thenException() {
        final var slot = 0;
        final var clientRuntimeRef = clientRuntimeRefModelFactory.create(clientId(), runtimeId());
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertClientRuntimeRefOperation.upsertClientRuntimeRef(slot, clientRuntimeRef));
    }

    @Test
    void givenClientRuntimeRef_whenUpsertClientRuntimeRef_thenIdempotencyViolation() {
        final var slot = 0;
        final var client = clientModelFactory.create(userId(), playerId(), versionId(), ClientConfigDto.create());
        upsertClientOperation.upsertClient(slot, client);

        final var clientRuntimeRef1 = clientRuntimeRefModelFactory.create(client.getId(), runtimeId());
        upsertClientRuntimeRefOperation.upsertClientRuntimeRef(slot, clientRuntimeRef1);

        final var clientRuntimeRef2 = clientRuntimeRefModelFactory.create(client.getId(),
                runtimeId(),
                clientRuntimeRef1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertClientRuntimeRefOperation.upsertClientRuntimeRef(slot, clientRuntimeRef2));
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