package com.omgservers.service.module.client.operation;

import com.omgservers.model.message.MessageQualifierEnum;
import com.omgservers.model.message.body.WelcomeMessageBodyModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.ClientMessageModelFactory;
import com.omgservers.service.factory.ClientModelFactory;
import com.omgservers.service.module.client.operation.testInterface.UpsertClientMessageOperationTestInterface;
import com.omgservers.service.module.client.operation.testInterface.UpsertClientOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertClientMessageOperationTest extends Assertions {

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
        final var client = clientModelFactory.create(userId(), playerId(), tenantId(), versionId(), matchmakerId());
        upsertClientOperation.upsertClient(shard, client);

        final var clientMessage = clientMessageModelFactory.create(client.getId(),
                MessageQualifierEnum.WELCOME_MESSAGE,
                new WelcomeMessageBodyModel(tenantId(), versionId()));
        final var changeContext = upsertClientMessageOperation.upsertClientMessage(shard, clientMessage);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenClientMessage_whenUpsertClientMessage_thenUpdated() {
        final var shard = 0;
        final var client = clientModelFactory.create(userId(), playerId(), tenantId(), versionId(), matchmakerId());
        upsertClientOperation.upsertClient(shard, client);

        final var clientMessage = clientMessageModelFactory.create(client.getId(),
                MessageQualifierEnum.WELCOME_MESSAGE,
                new WelcomeMessageBodyModel(tenantId(), versionId()));
        upsertClientMessageOperation.upsertClientMessage(shard, clientMessage);

        final var changeContext = upsertClientMessageOperation.upsertClientMessage(shard, clientMessage);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownId_whenUpsertClientMessage_thenException() {
        final var shard = 0;
        final var clientMessage = clientMessageModelFactory.create(clientId(),
                MessageQualifierEnum.WELCOME_MESSAGE,
                new WelcomeMessageBodyModel(tenantId(), versionId()));
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertClientMessageOperation.upsertClientMessage(shard, clientMessage));
    }

    @Test
    void givenClientMessage_whenUpsertClientMessage_thenIdempotencyViolation() {
        final var shard = 0;
        final var client = clientModelFactory.create(userId(), playerId(), tenantId(), versionId(), matchmakerId());
        upsertClientOperation.upsertClient(shard, client);

        final var clientMessage1 = clientMessageModelFactory.create(client.getId(),
                MessageQualifierEnum.WELCOME_MESSAGE,
                new WelcomeMessageBodyModel(tenantId(), versionId()));
        upsertClientMessageOperation.upsertClientMessage(shard, clientMessage1);

        final var clientMessage2 = clientMessageModelFactory.create(client.getId(),
                MessageQualifierEnum.WELCOME_MESSAGE,
                new WelcomeMessageBodyModel(tenantId(), versionId()),
                clientMessage1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertClientMessageOperation.upsertClientMessage(shard, clientMessage2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION, exception.getQualifier());
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

    Long clientId() {
        return generateIdOperation.generateId();
    }
}