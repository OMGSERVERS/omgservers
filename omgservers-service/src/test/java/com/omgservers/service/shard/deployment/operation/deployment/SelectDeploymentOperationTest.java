package com.omgservers.service.shard.deployment.operation.deployment;

import com.omgservers.BaseTestClass;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.deployment.operation.deployment.testInterface.SelectDeploymentOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectDeploymentOperationTest extends BaseTestClass {

    @Inject
    SelectDeploymentOperationTestInterface selectDeploymentOperation;

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
        final var deployment = selectDeploymentOperation.execute(
                testData.getDeployment().getId());
        assertEquals(testData.getDeployment(), deployment);
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        assertThrows(ServerSideNotFoundException.class, () ->
                selectDeploymentOperation.execute(generateIdOperation.generateId()));
    }
}