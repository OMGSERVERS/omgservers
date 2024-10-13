package com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive.testInterface.UpsertTenantFilesArchiveOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantFilesArchiveOperationTest extends BaseTestClass {

    @Inject
    UpsertTenantFilesArchiveOperationTestInterface upsertTenantFilesArchiveOperation;

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
    void givenTenantFilesArchive_whenExecute_thenInserted() {
        final var tenantFilesArchive = testData.getTenantFilesArchive();
        tenantFilesArchive.setId(generateIdOperation.generateId());
        tenantFilesArchive.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertTenantFilesArchiveOperation.execute(
                tenantFilesArchive);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_FILES_ARCHIVE_CREATED));
    }

    @Test
    void givenTenantFilesArchive_whenExecute_thenUpdated() {
        final var tenantFilesArchive = testData.getTenantFilesArchive();
        final var changeContext = upsertTenantFilesArchiveOperation.execute(
                tenantFilesArchive);

        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_FILES_ARCHIVE_CREATED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantFilesArchive = testData.getTenantFilesArchive();
        tenantFilesArchive.setId(generateIdOperation.generateId());
        tenantFilesArchive.setIdempotencyKey(generateIdOperation.generateStringId());
        tenantFilesArchive.setTenantId(generateIdOperation.generateId());
        tenantFilesArchive.setVersionId(generateIdOperation.generateId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertTenantFilesArchiveOperation.execute(
                tenantFilesArchive));
    }

    @Test
    void givenTenantFilesArchive_whenExecute_thenIdempotencyViolation() {
        final var tenantFilesArchive = testData.getTenantFilesArchive();
        tenantFilesArchive.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTenantFilesArchiveOperation.execute(tenantFilesArchive));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}