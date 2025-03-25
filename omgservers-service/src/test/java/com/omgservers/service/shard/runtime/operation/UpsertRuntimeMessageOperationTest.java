package com.omgservers.service.shard.runtime.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.runtime.operation.testInterface.UpsertRuntimeMessageOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertRuntimeMessageOperationTest extends BaseTestClass {

    @Inject
    UpsertRuntimeMessageOperationTestInterface upsertRuntimeMessageOperation;

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
        final var model = testData.getLobbyRuntimeCreatedRuntimeMessage();
        model.setId(generateIdOperation.generateId());
        model.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertRuntimeMessageOperation.execute(model);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenModel_whenExecute_thenUpdated() {
        final var model = testData.getLobbyRuntimeCreatedRuntimeMessage();

        final var changeContext = upsertRuntimeMessageOperation.execute(model);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenModel_whenExecute_thenException() {
        final var model = testData.getLobbyRuntimeCreatedRuntimeMessage();
        model.setId(generateIdOperation.generateId());
        model.setIdempotencyKey(generateIdOperation.generateStringId());
        model.setRuntimeId(generateIdOperation.generateId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertRuntimeMessageOperation.execute(model));
    }

    @Test
    void givenModel_whenExecute_thenIdempotencyViolation() {
        final var model = testData.getLobbyRuntimeCreatedRuntimeMessage();
        model.setId(generateIdOperation.generateId());

        final var exception =
                assertThrows(ServerSideConflictException.class, () -> upsertRuntimeMessageOperation.execute(model));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}