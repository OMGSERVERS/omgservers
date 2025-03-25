package com.omgservers.service.shard.deployment.operation.deployment;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.deployment.operation.deployment.testInterface.DeleteDeploymentOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteDeploymentOperationTest extends BaseTestClass {

    @Inject
    DeleteDeploymentOperationTestInterface deleteDeploymentOperation;

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
    void givenDeployment_whenExecute_thenDeleted() {
        final var changeContext = deleteDeploymentOperation.execute(
                testData.getDeployment().getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.DEPLOYMENT_DELETED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenFalse() {
        final var unknownDeploymentId = generateIdOperation.generateId();
        final var changeContext = deleteDeploymentOperation.execute(unknownDeploymentId);
        assertFalse(changeContext.getResult());
        assertTrue(changeContext.getChangeEvents().isEmpty());
    }
}