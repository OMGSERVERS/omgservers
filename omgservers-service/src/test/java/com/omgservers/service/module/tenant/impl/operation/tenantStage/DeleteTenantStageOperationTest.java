package com.omgservers.service.module.tenant.impl.operation.tenantStage;

import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.testInterface.DeleteTenantStageOperationTestInterface;
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
class DeleteTenantStageOperationTest extends Assertions {

    @Inject
    DeleteTenantStageOperationTestInterface deleteTenantStageOperation;

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
    void givenTenantStage_whenExecute_thenDeleted() {
        final var tenantStage = testData.getTenantStage();

        final var changeContext = deleteTenantStageOperation.execute(
                tenantStage.getTenantId(),
                tenantStage.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_STAGE_DELETED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenFalse() {
        final var changeContext = deleteTenantStageOperation.execute(
                generateIdOperation.generateId(),
                generateIdOperation.generateId());
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_STAGE_DELETED));
    }
}