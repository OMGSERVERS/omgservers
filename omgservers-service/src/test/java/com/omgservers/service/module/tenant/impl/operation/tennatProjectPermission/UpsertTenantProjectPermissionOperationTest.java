package com.omgservers.service.module.tenant.impl.operation.tennatProjectPermission;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.tennatProjectPermission.testInterface.UpsertTenantProjectPermissionOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantProjectPermissionOperationTest extends BaseTestClass {

    @Inject
    UpsertTenantProjectPermissionOperationTestInterface upsertTenantProjectPermissionOperation;

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
    void givenProjectPermission_whenExecute_thenInserted() {
        final var tenantProjectPermission = testData.getTenantProjectStageManagementPermission();
        tenantProjectPermission.setId(generateIdOperation.generateId());
        tenantProjectPermission.setUserId(generateIdOperation.generateId());
        tenantProjectPermission.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertTenantProjectPermissionOperation
                .execute(tenantProjectPermission);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenProjectPermission_whenExecute_thenUpdated() {
        final var tenantProjectPermission = testData.getTenantProjectStageManagementPermission();

        final var changeContext = upsertTenantProjectPermissionOperation
                .execute(tenantProjectPermission);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantProjectPermission = testData.getTenantProjectStageManagementPermission();
        tenantProjectPermission.setId(generateIdOperation.generateId());
        tenantProjectPermission.setTenantId(generateIdOperation.generateId());
        tenantProjectPermission.setProjectId(generateIdOperation.generateId());
        tenantProjectPermission.setIdempotencyKey(generateIdOperation.generateStringId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertTenantProjectPermissionOperation
                .execute(tenantProjectPermission));
    }

    @Test
    void givenProjectPermission_whenExecute_thenIdempotencyViolation() {
        final var tenantProjectPermission = testData.getTenantProjectStageManagementPermission();
        tenantProjectPermission.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTenantProjectPermissionOperation.execute(tenantProjectPermission));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}