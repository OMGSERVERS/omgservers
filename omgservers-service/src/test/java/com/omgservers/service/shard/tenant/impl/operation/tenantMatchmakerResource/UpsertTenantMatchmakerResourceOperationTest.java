package com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerResource;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerResource.testInterface.UpsertTenantMatchmakerResourceOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantMatchmakerResourceOperationTest extends BaseTestClass {

    @Inject
    UpsertTenantMatchmakerResourceOperationTestInterface upsertTenantMatchmakerResourceOperation;

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
    void givenTenantMatchmakerResource_whenExecute_thenInserted() {
        final var tenantMatchmakerResource = testData.getTenantMatchmakerResource();
        tenantMatchmakerResource.setId(generateIdOperation.generateId());
        tenantMatchmakerResource.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertTenantMatchmakerResourceOperation
                .execute(tenantMatchmakerResource);

        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_MATCHMAKER_RESOURCE_CREATED));
    }

    @Test
    void givenTenantMatchmakerResource_whenExecute_thenUpdated() {
        final var tenantMatchmakerResource = testData.getTenantMatchmakerResource();

        final var changeContext = upsertTenantMatchmakerResourceOperation
                .execute(tenantMatchmakerResource);

        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_MATCHMAKER_RESOURCE_CREATED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantMatchmakerResource = testData.getTenantMatchmakerResource();
        tenantMatchmakerResource.setId(generateIdOperation.generateId());
        tenantMatchmakerResource.setTenantId(generateIdOperation.generateId());
        tenantMatchmakerResource.setDeploymentId(generateIdOperation.generateId());
        tenantMatchmakerResource.setIdempotencyKey(generateIdOperation.generateStringId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertTenantMatchmakerResourceOperation
                .execute(tenantMatchmakerResource));
    }

    @Test
    void givenTenantMatchmakerResource_whenExecute_thenIdempotencyViolation() {
        final var tenantMatchmakerResource = testData.getTenantMatchmakerResource();
        tenantMatchmakerResource.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTenantMatchmakerResourceOperation.execute(tenantMatchmakerResource));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}