package com.omgservers.service.shard.client.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.client.ClientModelFactory;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.client.operation.testInterface.UpsertClientOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertClientOperationTest extends BaseTestClass {

    @Inject
    UpsertClientOperationTestInterface upsertClientOperation;

    @Inject
    ClientModelFactory clientModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenClient_whenUpsertClient_thenInserted() {
        final var shard = 0;
        final var client = clientModelFactory.create(userId(), playerId(), versionId());
        final var changeContext = upsertClientOperation.upsertClient(shard, client);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.CLIENT_CREATED));
    }

    @Test
    void givenClient_whenUpsertClient_thenUpdated() {
        final var shard = 0;
        final var client = clientModelFactory.create(userId(), playerId(), versionId());
        upsertClientOperation.upsertClient(shard, client);

        final var changeContext = upsertClientOperation.upsertClient(shard, client);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.CLIENT_CREATED));
    }

    @Test
    void givenClient_whenUpsertClient_thenIdempotencyViolation() {
        final var shard = 0;
        final var client1 = clientModelFactory.create(userId(), playerId(), versionId());
        upsertClientOperation.upsertClient(shard, client1);

        final var client2 = clientModelFactory.create(userId(),
                playerId(),
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

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }
}