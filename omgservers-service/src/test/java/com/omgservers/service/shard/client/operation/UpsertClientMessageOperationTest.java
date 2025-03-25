package com.omgservers.service.shard.client.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.message.body.ClientGreetedMessageBodyDto;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.factory.client.ClientModelFactory;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.client.operation.testInterface.UpsertClientMessageOperationTestInterface;
import com.omgservers.service.shard.client.operation.testInterface.UpsertClientOperationTestInterface;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Instant;

@Slf4j
@QuarkusTest
class UpsertClientMessageOperationTest extends BaseTestClass {

    @Inject
    UpsertClientOperationTestInterface upsertClientOperation;

    @Inject
    UpsertClientMessageOperationTestInterface upsertClientMessageOperation;

    @Inject
    ClientModelFactory clientModelFactory;

    @Inject
    ClientMessageModelFactory clientMessageModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenClientMessage_whenUpsertClientMessage_thenInserted() {
        final var shard = 0;
        final var client = clientModelFactory.create(userId(), playerId(), versionId());
        upsertClientOperation.upsertClient(shard, client);

        final var clientMessage = clientMessageModelFactory.create(client.getId(),
                new ClientGreetedMessageBodyDto(tenantId(), versionId(), Instant.now()));
        final var changeContext = upsertClientMessageOperation.upsertClientMessage(shard, clientMessage);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenClientMessage_whenUpsertClientMessage_thenUpdated() {
        final var shard = 0;
        final var client = clientModelFactory.create(userId(), playerId(), versionId());
        upsertClientOperation.upsertClient(shard, client);

        final var clientMessage = clientMessageModelFactory.create(client.getId(),
                new ClientGreetedMessageBodyDto(tenantId(), versionId(), Instant.now()));
        upsertClientMessageOperation.upsertClientMessage(shard, clientMessage);

        final var changeContext = upsertClientMessageOperation.upsertClientMessage(shard, clientMessage);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownId_whenUpsertClientMessage_thenException() {
        final var shard = 0;
        final var clientMessage = clientMessageModelFactory.create(clientId(),
                new ClientGreetedMessageBodyDto(tenantId(), versionId(), Instant.now()));
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertClientMessageOperation.upsertClientMessage(shard, clientMessage));
    }

    @Test
    void givenClientMessage_whenUpsertClientMessage_thenIdempotencyViolation() {
        final var shard = 0;
        final var client = clientModelFactory.create(userId(), playerId(), versionId());
        upsertClientOperation.upsertClient(shard, client);

        final var clientMessage1 = clientMessageModelFactory.create(client.getId(),
                new ClientGreetedMessageBodyDto(tenantId(), versionId(), Instant.now()));
        upsertClientMessageOperation.upsertClientMessage(shard, clientMessage1);

        final var clientMessage2 = clientMessageModelFactory.create(client.getId(),
                new ClientGreetedMessageBodyDto(tenantId(), versionId(), Instant.now()),
                clientMessage1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertClientMessageOperation.upsertClientMessage(shard, clientMessage2));
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
}