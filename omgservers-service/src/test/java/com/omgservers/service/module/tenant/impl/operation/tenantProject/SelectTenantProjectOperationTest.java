package com.omgservers.service.module.tenant.impl.operation.tenantProject;

import com.omgservers.BaseTestClass;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantProject.testInterface.SelectTenantProjectOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectTenantProjectOperationTest extends BaseTestClass {

    @Inject
    SelectTenantProjectOperationTestInterface selectProjectOperation;

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
    void givenTenantProject_whenExecute_thenSelected() {
        final var tenantProject = selectProjectOperation.execute(
                testData.getTenantProject().getTenantId(),
                testData.getTenantProject().getId());
        assertEquals(testData.getTenantProject(), tenantProject);
    }

    @Test
    void givenUnknownId_whenExecute_thenException() {
        assertThrows(ServerSideNotFoundException.class, () -> selectProjectOperation.execute(
                generateIdOperation.generateId(), generateIdOperation.generateId()));
    }
}