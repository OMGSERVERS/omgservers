package com.omgservers.service.shard.deployment.operation.deploymentLobbyResource;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.deployment.operation.deploymentLobbyResource.testInterface.UpsertDeploymentLobbyResourceOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertDeploymentLobbyResourceOperationTest extends BaseTestClass {

    @Inject
    UpsertDeploymentLobbyResourceOperationTestInterface upsertDeploymentLobbyResourceOperation;

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
        final var tenantLobbyResource = testData.getDeploymentLobbyResource();
        tenantLobbyResource.setId(generateIdOperation.generateId());
        tenantLobbyResource.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertDeploymentLobbyResourceOperation.execute(tenantLobbyResource);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.DEPLOYMENT_LOBBY_RESOURCE_CREATED));
    }

    @Test
    void givenResource_whenExecute_thenUpdated() {
        final var tenantLobbyResource = testData.getDeploymentLobbyResource();

        final var changeContext = upsertDeploymentLobbyResourceOperation
                .execute(tenantLobbyResource);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.DEPLOYMENT_LOBBY_RESOURCE_CREATED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantLobbyResource = testData.getDeploymentLobbyResource();
        tenantLobbyResource.setId(generateIdOperation.generateId());
        tenantLobbyResource.setDeploymentId(generateIdOperation.generateId());
        tenantLobbyResource.setIdempotencyKey(generateIdOperation.generateStringId());
        assertThrows(ServerSideBadRequestException.class, () -> upsertDeploymentLobbyResourceOperation
                .execute(tenantLobbyResource));
    }

    @Test
    void givenResource_whenExecute_thenIdempotencyViolation() {
        final var deploymentLobbyResource = testData.getDeploymentLobbyResource();
        deploymentLobbyResource.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertDeploymentLobbyResourceOperation.execute(deploymentLobbyResource));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}