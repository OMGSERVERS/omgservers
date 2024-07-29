package com.omgservers.service.module.client.operation;

import com.omgservers.schema.model.message.MessageQualifierEnum;
import com.omgservers.schema.model.message.body.ServerWelcomeMessageBodyModel;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.client.ClientMessageModelFactory;
import com.omgservers.service.factory.client.ClientModelFactory;
import com.omgservers.service.module.client.operation.testInterface.UpsertClientMessageOperationTestInterface;
import com.omgservers.service.module.client.operation.testInterface.UpsertClientOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

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
        final var client = clientModelFactory.create(userId(), playerId(), tenantId(), versionId());
        upsertClientOperation.upsertClient(shard, client);

        final var clientMessage = clientMessageModelFactory.create(client.getId(),
                MessageQualifierEnum.SERVER_WELCOME_MESSAGE,
                new ServerWelcomeMessageBodyModel(tenantId(), versionId(), Instant.now()));
        final var changeContext = upsertClientMessageOperation.upsertClientMessage(shard, clientMessage);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenClientMessage_whenUpsertClientMessage_thenUpdated() {
        final var shard = 0;
        final var client = clientModelFactory.create(userId(), playerId(), tenantId(), versionId());
        upsertClientOperation.upsertClient(shard, client);

        final var clientMessage = clientMessageModelFactory.create(client.getId(),
                MessageQualifierEnum.SERVER_WELCOME_MESSAGE,
                new ServerWelcomeMessageBodyModel(tenantId(), versionId(), Instant.now()));
        upsertClientMessageOperation.upsertClientMessage(shard, clientMessage);

        final var changeContext = upsertClientMessageOperation.upsertClientMessage(shard, clientMessage);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownId_whenUpsertClientMessage_thenException() {
        final var shard = 0;
        final var clientMessage = clientMessageModelFactory.create(clientId(),
                MessageQualifierEnum.SERVER_WELCOME_MESSAGE,
                new ServerWelcomeMessageBodyModel(tenantId(), versionId(), Instant.now()));
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertClientMessageOperation.upsertClientMessage(shard, clientMessage));
    }

    @Test
    void givenClientMessage_whenUpsertClientMessage_thenIdempotencyViolation() {
        final var shard = 0;
        final var client = clientModelFactory.create(userId(), playerId(), tenantId(), versionId());
        upsertClientOperation.upsertClient(shard, client);

        final var clientMessage1 = clientMessageModelFactory.create(client.getId(),
                MessageQualifierEnum.SERVER_WELCOME_MESSAGE,
                new ServerWelcomeMessageBodyModel(tenantId(), versionId(), Instant.now()));
        upsertClientMessageOperation.upsertClientMessage(shard, clientMessage1);

        final var clientMessage2 = clientMessageModelFactory.create(client.getId(),
                MessageQualifierEnum.SERVER_WELCOME_MESSAGE,
                new ServerWelcomeMessageBodyModel(tenantId(), versionId(), Instant.now()),
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