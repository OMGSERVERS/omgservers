package com.omgservers.service.module.tenant.impl.operation.tenantVersion;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.tenantVersion.testInterface.UpsertTenantVersionOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantVersionOperationTest extends BaseTestClass {

    @Inject
    UpsertTenantVersionOperationTestInterface upsertTenantVersionOperation;

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
    void givenTenantVersion_whenExecute_thenInserted() {
        final var tenantVersion = testData.getTenantVersion();
        tenantVersion.setId(generateIdOperation.generateId());
        tenantVersion.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertTenantVersionOperation.execute(tenantVersion);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_VERSION_CREATED));
    }

    @Test
    void givenTenantVersion_whenExecute_thenUpdated() {
        final var tenantVersion = testData.getTenantVersion();

        final var changeContext = upsertTenantVersionOperation.execute(tenantVersion);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_VERSION_CREATED));
    }

    @Test
    void givenTenantVersion_whenExecute_thenIdempotencyViolation() {
        final var tenantVersion = testData.getTenantVersion();
        tenantVersion.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTenantVersionOperation.execute(tenantVersion));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}
