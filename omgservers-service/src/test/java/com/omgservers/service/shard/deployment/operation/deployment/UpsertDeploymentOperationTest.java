package com.omgservers.service.shard.deployment.operation.deployment;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.deployment.operation.deployment.testInterface.UpsertDeploymentOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertDeploymentOperationTest extends BaseTestClass {

    @Inject
    UpsertDeploymentOperationTestInterface upsertDeploymentOperation;

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
    void givenDeployment_whenExecute_thenInserted() {
        final var deployment = testData.getDeployment();
        deployment.setId(generateIdOperation.generateId());
        deployment.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertDeploymentOperation.execute(deployment);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.DEPLOYMENT_CREATED));
    }

    @Test
    void givenDeployment_whenExecute_thenUpdated() {
        final var deployment = testData.getDeployment();
        final var changeContext = upsertDeploymentOperation.execute(deployment);

        assertFalse(changeContext.getResult());
        assertTrue(changeContext.getChangeEvents().isEmpty());
    }

    @Test
    void givenDeployment_whenExecute_thenIdempotencyViolation() {
        final var deployment = testData.getDeployment();
        deployment.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertDeploymentOperation.execute(deployment));

        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}