package com.omgservers.service.module.client.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.client.ClientModelFactory;
import com.omgservers.service.module.client.operation.testInterface.UpsertClientOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertClientOperationTest extends Assertions {

    @Inject
    UpsertClientOperationTestInterface upsertClientOperation;

    @Inject
    ClientModelFactory clientModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenClient_whenUpsertClient_thenInserted() {
        final var shard = 0;
        final var client = clientModelFactory.create(userId(), playerId(), tenantId(), versionId());
        final var changeContext = upsertClientOperation.upsertClient(shard, client);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.CLIENT_CREATED));
    }

    @Test
    void givenClient_whenUpsertClient_thenUpdated() {
        final var shard = 0;
        final var client = clientModelFactory.create(userId(), playerId(), tenantId(), versionId());
        upsertClientOperation.upsertClient(shard, client);

        final var changeContext = upsertClientOperation.upsertClient(shard, client);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.CLIENT_CREATED));
    }

    @Test
    void givenClient_whenUpsertClient_thenIdempotencyViolation() {
        final var shard = 0;
        final var client1 = clientModelFactory.create(userId(), playerId(), tenantId(), versionId());
        upsertClientOperation.upsertClient(shard, client1);

        final var client2 = clientModelFactory.create(userId(),
                playerId(),
                tenantId(),
                versionId(),
                matchmakerId(),
                client1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertClientOperation.upsertClient(shard, client2));
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

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }
}