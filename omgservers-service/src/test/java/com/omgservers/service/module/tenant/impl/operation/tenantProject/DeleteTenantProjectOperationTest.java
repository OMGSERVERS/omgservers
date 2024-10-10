package com.omgservers.service.module.tenant.impl.operation.tenantProject;

import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.module.tenant.impl.operation.tenantProject.testInterface.DeleteTenantProjectOperationTestInterface;
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
class DeleteTenantProjectOperationTest extends Assertions {

    @Inject
    DeleteTenantProjectOperationTestInterface deleteProjectOperation;

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
    void givenTenantProject_whenExecute_thenDeleted() {
        final var tenantProject = testData.getTenantProject();

        final var changeContext = deleteProjectOperation.execute(
                tenantProject.getTenantId(),
                tenantProject.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_PROJECT_DELETED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenFalse() {
        final var changeContext = deleteProjectOperation.execute(
                generateIdOperation.generateId(),
                generateIdOperation.generateId());
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_PROJECT_DELETED));
    }
}