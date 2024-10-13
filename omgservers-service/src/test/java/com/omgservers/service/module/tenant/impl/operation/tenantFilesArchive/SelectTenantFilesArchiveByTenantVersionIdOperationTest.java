package com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantFilesArchive.testInterface.SelectTenantFilesArchiveByTenantVersionIdOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectTenantFilesArchiveByTenantVersionIdOperationTest extends BaseTestClass {

    @Inject
    SelectTenantFilesArchiveByTenantVersionIdOperationTestInterface
            selectTenantFilesArchiveByTenantVersionIdOperationTestInterface;

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
        final var tenantFilesArchive = selectTenantFilesArchiveByTenantVersionIdOperationTestInterface
                .execute(testData.getTenantVersion().getTenantId(),
                        testData.getTenantVersion().getId());
        assertEquals(testData.getTenantFilesArchive(), tenantFilesArchive);
    }

    @Test
    void givenTenantFilesArchive_whenExecute_thenNotFound() {
        final var exception = assertThrows(ServerSideNotFoundException.class,
                () -> selectTenantFilesArchiveByTenantVersionIdOperationTestInterface
                        .execute(generateIdOperation.generateId(),
                                generateIdOperation.generateId()));
        assertEquals(ExceptionQualifierEnum.OBJECT_NOT_FOUND, exception.getQualifier());
    }
}