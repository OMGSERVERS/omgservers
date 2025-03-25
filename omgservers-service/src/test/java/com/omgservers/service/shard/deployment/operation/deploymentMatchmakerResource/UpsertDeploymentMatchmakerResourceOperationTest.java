package com.omgservers.service.shard.deployment.operation.deploymentMatchmakerResource;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.deployment.operation.deploymentMatchmakerResource.testInterface.UpsertDeploymentMatchmakerResourceOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertDeploymentMatchmakerResourceOperationTest extends BaseTestClass {

    @Inject
    UpsertDeploymentMatchmakerResourceOperationTestInterface upsertDeploymentMatchmakerResourceOperation;

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
    void givenResource_whenExecute_thenInserted() {
        final var tenantMatchmakerResource = testData.getDeploymentMatchmakerResource();
        tenantMatchmakerResource.setId(generateIdOperation.generateId());
        tenantMatchmakerResource.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertDeploymentMatchmakerResourceOperation
                .execute(tenantMatchmakerResource);

        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.DEPLOYMENT_MATCHMAKER_RESOURCE_CREATED));
    }

    @Test
    void givenResource_whenExecute_thenUpdated() {
        final var tenantMatchmakerResource = testData.getDeploymentMatchmakerResource();

        final var changeContext = upsertDeploymentMatchmakerResourceOperation
                .execute(tenantMatchmakerResource);

        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.DEPLOYMENT_MATCHMAKER_RESOURCE_CREATED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantMatchmakerResource = testData.getDeploymentMatchmakerResource();
        tenantMatchmakerResource.setId(generateIdOperation.generateId());
        tenantMatchmakerResource.setDeploymentId(generateIdOperation.generateId());
        tenantMatchmakerResource.setIdempotencyKey(generateIdOperation.generateStringId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertDeploymentMatchmakerResourceOperation
                .execute(tenantMatchmakerResource));
    }

    @Test
    void givenResource_whenExecute_thenIdempotencyViolation() {
        final var tenantMatchmakerResource = testData.getDeploymentMatchmakerResource();
        tenantMatchmakerResource.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertDeploymentMatchmakerResourceOperation.execute(tenantMatchmakerResource));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}