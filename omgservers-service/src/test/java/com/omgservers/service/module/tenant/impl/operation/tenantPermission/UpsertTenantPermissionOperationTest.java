package com.omgservers.service.module.tenant.impl.operation.tenantPermission;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.tenantPermission.testInterface.UpsertTenantPermissionOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantPermissionOperationTest extends BaseTestClass {

    @Inject
    UpsertTenantPermissionOperationTestInterface upsertTenantPermissionOperation;

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
    void givenTenantPermission_whenExecute_thenInserted() {
        final var tenantPermission = testData.getTenantProjectManagerPermission();
        tenantPermission.setId(generateIdOperation.generateId());
        tenantPermission.setUserId(generateIdOperation.generateId());
        tenantPermission.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertTenantPermissionOperation.execute(tenantPermission);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenTenantPermission_whenExecute_thenUpdated() {
        final var tenantPermission = testData.getTenantProjectManagerPermission();

        final var changeContext = upsertTenantPermissionOperation.execute(tenantPermission);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantPermission = testData.getTenantProjectManagerPermission();
        tenantPermission.setId(generateIdOperation.generateId());
        tenantPermission.setTenantId(generateIdOperation.generateId());
        tenantPermission.setIdempotencyKey(generateIdOperation.generateStringId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertTenantPermissionOperation
                .execute(tenantPermission));
    }

    @Test
    void givenTenantPermission_whenExecute_thenIdempotencyViolation() {
        final var tenantPermission = testData.getTenantProjectManagerPermission();
        tenantPermission.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTenantPermissionOperation.execute(tenantPermission));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}