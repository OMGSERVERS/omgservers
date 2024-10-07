package com.omgservers.service.module.tenant.impl.operation.tenantProject;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.tenantProject.testInterface.UpsertTenantProjectOperationTestInterface;
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
class UpsertTenantProjectOperationTest extends Assertions {

    @Inject
    UpsertTenantProjectOperationTestInterface upsertProjectOperation;

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
    void givenTenantProject_whenExecute_thenInserted() {
        final var tenantProject = testData.getTenantProject();
        tenantProject.setId(generateIdOperation.generateId());
        tenantProject.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertProjectOperation.execute(tenantProject);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_PROJECT_CREATED));
    }

    @Test
    void givenTenantProject_whenExecute_thenUpdated() {
        final var tenantProject = testData.getTenantProject();

        final var changeContext = upsertProjectOperation.execute(tenantProject);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_PROJECT_CREATED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantProject = testData.getTenantProject();
        tenantProject.setId(generateIdOperation.generateId());
        tenantProject.setTenantId(generateIdOperation.generateId());
        tenantProject.setIdempotencyKey(generateIdOperation.generateStringId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertProjectOperation.execute(tenantProject));
    }

    @Test
    void givenTenantProject_whenExecute_thenIdempotencyViolation() {
        final var tenantProject = testData.getTenantProject();
        tenantProject.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertProjectOperation.execute(tenantProject));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}