package com.omgservers.service.module.tenant.impl.operation.tenantStagePermission;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.tenantStagePermission.testInterface.UpsertTenantStagePermissionOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantStagePermissionOperationTest extends BaseTestClass {

    @Inject
    UpsertTenantStagePermissionOperationTestInterface upsertStagePermissionOperation;

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
    void givenTenantStagePermission_whenExecute_thenInserted() {
        final var tenantStagePermission = testData.getTenantStageDeploymentManagerPermission();
        tenantStagePermission.setId(generateIdOperation.generateId());
        tenantStagePermission.setUserId(generateIdOperation.generateId());
        tenantStagePermission.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertStagePermissionOperation.execute(tenantStagePermission);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenTenantStagePermission_whenExecute_thenUpdated() {
        final var tenantStagePermission = testData.getTenantStageDeploymentManagerPermission();

        final var changeContext = upsertStagePermissionOperation.execute(tenantStagePermission);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantStagePermission = testData.getTenantStageDeploymentManagerPermission();
        tenantStagePermission.setId(generateIdOperation.generateId());
        tenantStagePermission.setTenantId(generateIdOperation.generateId());
        tenantStagePermission.setStageId(generateIdOperation.generateId());
        tenantStagePermission.setIdempotencyKey(generateIdOperation.generateStringId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertStagePermissionOperation
                .execute(tenantStagePermission));
    }

    @Test
    void givenTenantStagePermission_whenExecute_thenIdempotencyViolation() {
        final var tenantStagePermission = testData.getTenantStageDeploymentManagerPermission();
        tenantStagePermission.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertStagePermissionOperation.execute(tenantStagePermission));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}