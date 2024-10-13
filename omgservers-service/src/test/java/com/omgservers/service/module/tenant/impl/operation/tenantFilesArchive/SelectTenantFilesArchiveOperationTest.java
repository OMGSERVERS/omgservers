package com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive;

import com.omgservers.BaseTestClass;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive.testInterface.SelectTenantFilesArchiveOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectTenantFilesArchiveOperationTest extends BaseTestClass {

    @Inject
    SelectTenantFilesArchiveOperationTestInterface selectTenantFilesArchiveOperation;

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
    void givenTenantFilesArchive_whenExecute_thenSelected() {
        final var tenantFilesArchive = selectTenantFilesArchiveOperation.execute(
                testData.getTenantFilesArchive().getTenantId(),
                testData.getTenantFilesArchive().getId());
        assertEquals(testData.getTenantFilesArchive(), tenantFilesArchive);
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        assertThrows(ServerSideNotFoundException.class, () -> selectTenantFilesArchiveOperation.execute(
                generateIdOperation.generateId(),
                generateIdOperation.generateId()));
    }
}