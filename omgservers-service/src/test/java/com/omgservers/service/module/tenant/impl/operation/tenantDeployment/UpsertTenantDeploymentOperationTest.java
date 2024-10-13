package com.omgservers.service.module.tenant.impl.operation.tenantDeployment;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.tenantDeployment.testInterface.UpsertTenantDeploymentOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantDeploymentOperationTest extends BaseTestClass {

    @Inject
    UpsertTenantDeploymentOperationTestInterface upsertTenantDeploymentOperation;

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
    void givenTenantDeployment_whenExecute_thenInserted() {
        final var tenantDeployment = testData.getTenantDeployment();
        tenantDeployment.setId(generateIdOperation.generateId());
        tenantDeployment.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertTenantDeploymentOperation.execute(
                tenantDeployment);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_DEPLOYMENT_CREATED));
    }

    @Test
    void givenTenantDeployment_whenExecute_thenUpdated() {
        final var tenantDeployment = testData.getTenantDeployment();

        final var changeContext = upsertTenantDeploymentOperation.execute(tenantDeployment);

        assertFalse(changeContext.getResult());
        assertTrue(changeContext.getChangeEvents().isEmpty());
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantDeployment = testData.getTenantDeployment();
        tenantDeployment.setId(generateIdOperation.generateId());
        tenantDeployment.setIdempotencyKey(generateIdOperation.generateStringId());
        tenantDeployment.setTenantId(generateIdOperation.generateId());
        tenantDeployment.setVersionId(generateIdOperation.generateId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertTenantDeploymentOperation.execute(
                tenantDeployment));
    }

    @Test
    void givenTenantDeployment_whenExecute_thenIdempotencyViolation() {
        final var tenantDeployment = testData.getTenantDeployment();
        tenantDeployment.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTenantDeploymentOperation.execute(tenantDeployment));

        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}