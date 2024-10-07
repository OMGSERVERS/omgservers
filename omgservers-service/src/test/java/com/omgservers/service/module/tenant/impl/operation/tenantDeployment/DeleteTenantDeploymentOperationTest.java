package com.omgservers.service.module.tenant.impl.operation.tenantDeployment;

import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.module.tenant.impl.operation.tenantDeployment.testInterface.DeleteTenantDeploymentOperationTestInterface;
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
class DeleteTenantDeploymentOperationTest extends Assertions {

    @Inject
    DeleteTenantDeploymentOperationTestInterface deleteTenantDeploymentOperation;

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
    void givenTenantDeployment_whenExecute_thenDeleted() {
        final var changeContext = deleteTenantDeploymentOperation.execute(
                testData.getTenantDeployment().getTenantId(),
                testData.getTenantDeployment().getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_DEPLOYMENT_DELETED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenFalse() {
        final var changeContext = deleteTenantDeploymentOperation.execute(
                generateIdOperation.generateId(),
                generateIdOperation.generateId());
        assertFalse(changeContext.getResult());
        assertTrue(changeContext.getChangeEvents().isEmpty());
    }
}