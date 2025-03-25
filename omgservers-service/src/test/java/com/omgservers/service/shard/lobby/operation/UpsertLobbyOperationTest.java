package com.omgservers.service.shard.lobby.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.lobby.operation.testInterface.UpsertLobbyOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertLobbyOperationTest extends BaseTestClass {

    @Inject
    UpsertLobbyOperationTestInterface upsertLobbyOperation;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Inject
    TestDataFactory testDataFactory;

    TestDataFactory.DefaultTestData testData;

    @BeforeEach
    void beforeEach() {
        testData = testDataFactory.createDefaultTestData();
    }

    @Test
    void givenModel_whenExecute_thenInserted() {
        final var model = testData.getLobby();
        model.setId(generateIdOperation.generateId());
        model.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertLobbyOperation.upsertLobby(model);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.LOBBY_CREATED));
    }

    @Test
    void givenModel_whenExecute_thenUpdated() {
        final var model = testData.getLobby();

        final var changeContext = upsertLobbyOperation.upsertLobby(model);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.LOBBY_CREATED));
    }

    @Test
    void givenModel_whenExecute_thenIdempotencyViolation() {
        final var model = testData.getLobby();
        model.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertLobbyOperation.upsertLobby(model));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}