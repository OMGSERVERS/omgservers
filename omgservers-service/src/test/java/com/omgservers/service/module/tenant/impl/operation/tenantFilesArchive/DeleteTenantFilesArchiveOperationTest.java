package com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive.testInterface.DeleteTenantFilesArchiveOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteTenantFilesArchiveOperationTest extends BaseTestClass {

    @Inject
    DeleteTenantFilesArchiveOperationTestInterface deleteTenantFilesArchiveOperation;

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
    void givenTenantFilesArchive_whenDeleteTenantFilesArchive_thenDeleted() {
        final var changeContext = deleteTenantFilesArchiveOperation.execute(
                testData.getTenantFilesArchive().getTenantId(),
                testData.getTenantFilesArchive().getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_FILES_ARCHIVE_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteTenantFilesArchive_thenFalse() {
        final var changeContext = deleteTenantFilesArchiveOperation.execute(
                generateIdOperation.generateId(),
                generateIdOperation.generateId());
        assertFalse(changeContext.getResult());
        assertTrue(changeContext.getChangeEvents().isEmpty());
    }
}