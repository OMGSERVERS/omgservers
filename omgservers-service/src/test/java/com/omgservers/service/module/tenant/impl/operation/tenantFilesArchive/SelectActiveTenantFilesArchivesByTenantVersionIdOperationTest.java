package com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive;

import com.omgservers.BaseTestClass;
import com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive.testInterface.SelectActiveTenantFilesArchivesProjectionsByTenantVersionIdOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectActiveTenantFilesArchivesByTenantVersionIdOperationTest extends BaseTestClass {

    @Inject
    SelectActiveTenantFilesArchivesProjectionsByTenantVersionIdOperationTestInterface
            selectActiveTenantFilesArchivesProjectionsByTenantVersionIdOperationTestInterface;

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
    void givenTenantFilesArchives_whenExecute_thenSelected() {
        final var tenantVersion2 = testDataFactory.getTenantTestDataFactory()
                .createTenantVersion(testData.getTenantProject());
        final var tenantFilesArchive21 =
                testDataFactory.getTenantTestDataFactory().createTenantFilesArchive(tenantVersion2).mapToProjection();
        final var tenantFilesArchive22 =
                testDataFactory.getTenantTestDataFactory().createTenantFilesArchive(tenantVersion2).mapToProjection();

        final var tenantFilesArchives =
                selectActiveTenantFilesArchivesProjectionsByTenantVersionIdOperationTestInterface
                        .execute(tenantVersion2.getTenantId(), tenantVersion2.getId());
        assertEquals(2, tenantFilesArchives.size());
        assertTrue(tenantFilesArchives.contains(tenantFilesArchive21));
        assertTrue(tenantFilesArchives.contains(tenantFilesArchive22));
    }
}