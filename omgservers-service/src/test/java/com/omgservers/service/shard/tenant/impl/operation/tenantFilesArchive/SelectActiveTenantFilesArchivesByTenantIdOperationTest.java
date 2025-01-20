package com.omgservers.service.shard.tenant.impl.operation.tenantFilesArchive;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.tenant.impl.operation.tenantFilesArchive.testInterface.SelectActiveTenantFilesArchivesProjectionsByTenantIdOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectActiveTenantFilesArchivesByTenantIdOperationTest extends BaseTestClass {

    @Inject
    SelectActiveTenantFilesArchivesProjectionsByTenantIdOperationTestInterface
            selectActiveTenantFilesArchivesProjectionsByTenantIdOperationTestInterface;

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
        final var tenantVersion1 = testData.getTenantVersion();
        final var tenantFilesArchive11 = testData.getTenantFilesArchive()
                .mapToProjection();

        final var tenantVersion2 = testDataFactory.getTenantTestDataFactory()
                .createTenantVersion(testData.getTenantProject());
        final var tenantFilesArchive21 =
                testDataFactory.getTenantTestDataFactory().createTenantFilesArchive(tenantVersion2).mapToProjection();
        final var tenantFilesArchive22 =
                testDataFactory.getTenantTestDataFactory().createTenantFilesArchive(tenantVersion2).mapToProjection();

        final var tenantFilesArchives =
                selectActiveTenantFilesArchivesProjectionsByTenantIdOperationTestInterface
                        .execute(testData.getTenant().getId());
        assertEquals(3, tenantFilesArchives.size());
        assertTrue(tenantFilesArchives.contains(tenantFilesArchive11));
        assertTrue(tenantFilesArchives.contains(tenantFilesArchive21));
        assertTrue(tenantFilesArchives.contains(tenantFilesArchive22));
    }
}