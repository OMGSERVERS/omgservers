package com.omgservers.service.shard.tenant.impl.operation.tenantDeployment;

import com.omgservers.BaseTestClass;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeployment.testInterface.SelectTenantDeploymentOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectTenantDeploymentOperationTest extends BaseTestClass {

    @Inject
    SelectTenantDeploymentOperationTestInterface selectTenantDeploymentOperation;

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
    void givenTenantDeployment_whenExecute_thenSelected() {
        final var tenantDeployment = selectTenantDeploymentOperation.execute(
                testData.getTenantDeployment().getTenantId(),
                testData.getTenantDeployment().getId());
        assertEquals(testData.getTenantDeployment(), tenantDeployment);
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        assertThrows(ServerSideNotFoundException.class, () -> selectTenantDeploymentOperation.execute(
                generateIdOperation.generateId(),
                generateIdOperation.generateId()));
    }
}