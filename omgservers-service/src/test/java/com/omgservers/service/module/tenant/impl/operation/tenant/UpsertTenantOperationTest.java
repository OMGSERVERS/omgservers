package com.omgservers.service.module.tenant.impl.operation.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.tenant.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantOperationTest extends BaseTestClass {

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    TestDataFactory testDataFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void whenExecute_thenInserted() {
        final var testData = testDataFactory.createDefaultTestData();
        final var tenant = testData.getTenant();
        tenant.setId(generateIdOperation.generateId());
        tenant.setIdempotencyKey(generateIdOperation.generateStringId());
        final var changeContext = upsertTenantOperation.upsertTenant(tenant);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenTenant_whenExecute_thenUpdated() {
        final var testData = testDataFactory.createDefaultTestData();

        final var changeContext = upsertTenantOperation.upsertTenant(testData.getTenant());
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenTenant_whenExecute_thenIdempotencyViolation() {
        final var testData = testDataFactory.createDefaultTestData();

        final var tenant = testData.getTenant();
        tenant.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTenantOperation.upsertTenant(tenant));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}