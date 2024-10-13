package com.omgservers.service.module.tenant.impl.operation.tenantImage;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantImage.TenantImageQualifierEnum;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenantImage.testInterface.SelectTenantImageByTenantVersionIdAndQualifierOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectTenantImageByTenantVersionIdAndQualifierOperationTest extends BaseTestClass {

    @Inject
    SelectTenantImageByTenantVersionIdAndQualifierOperationTestInterface
            selectTenantImageByTenantVersionIdAndQualifierOperationTestInterface;

    @Inject
    TestDataFactory testDataFactory;

    TestDataFactory.DefaultTestData testData;

    @BeforeEach
    void beforeEach() {
        testData = testDataFactory.createDefaultTestData();
    }

    @Test
    void givenTenantImage_whenExecute_thenSelected() {
        final var tenantImage = selectTenantImageByTenantVersionIdAndQualifierOperationTestInterface
                .execute(testData.getTenantVersion().getTenantId(),
                        testData.getTenantVersion().getId(),
                        TenantImageQualifierEnum.UNIVERSAL);
        assertEquals(testData.getTenantImage(), tenantImage);
    }

    @Test
    void givenTenantImage_whenExecute_thenNotFound() {
        final var exception = assertThrows(ServerSideNotFoundException.class,
                () -> selectTenantImageByTenantVersionIdAndQualifierOperationTestInterface
                        .execute(testData.getTenantVersion().getTenantId(),
                                testData.getTenantVersion().getId(),
                                TenantImageQualifierEnum.LOBBY));
        assertEquals(ExceptionQualifierEnum.OBJECT_NOT_FOUND, exception.getQualifier());
    }
}