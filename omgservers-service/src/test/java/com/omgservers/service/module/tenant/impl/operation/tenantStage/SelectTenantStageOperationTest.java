package com.omgservers.service.module.tenant.impl.operation.tenantStage;

import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.testInterface.SelectTenantStageOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectTenantStageOperationTest extends Assertions {

    @Inject
    SelectTenantStageOperationTestInterface selectTenantStageOperation;

    @Inject
    TestDataFactory testDataFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    TestDataFactory.DefaultTestData testData;

    @BeforeEach
    void beforeEach() {
        testData = testDataFactory.createDefaultTestData();
    }

    @Test
    void givenTenantStage_whenExecute_thenSelected() {
        final var tenantStage = selectTenantStageOperation.execute(
                testData.getTenantStage().getTenantId(),
                testData.getTenantStage().getId());
        assertEquals(testData.getTenantStage(), tenantStage);
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        assertThrows(ServerSideNotFoundException.class, () -> selectTenantStageOperation
                .execute(generateIdOperation.generateId(), generateIdOperation.generateId()));
    }
}