package com.omgservers.service.shard.runtime.operation;

import com.omgservers.BaseTestClass;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.runtime.operation.testInterface.DeleteRuntimeMessageOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteRuntimeMessageOperationTest extends BaseTestClass {

    @Inject
    DeleteRuntimeMessageOperationTestInterface deleteRuntimeMessageOperation;

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
    void givenModel_whenExecute_thenDeleted() {
        final var model = testData.getLobbyRuntimeCreatedRuntimeMessage();

        final var changeContext = deleteRuntimeMessageOperation.execute(model.getRuntimeId(), model.getId());
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenExecute_thenFalse() {
        final var model = testData.getLobbyRuntimeCreatedRuntimeMessage();
        model.setRuntimeId(generateIdOperation.generateId());

        final var changeContext = deleteRuntimeMessageOperation.execute(model.getRuntimeId(), model.getId());
        assertFalse(changeContext.getResult());
    }
}