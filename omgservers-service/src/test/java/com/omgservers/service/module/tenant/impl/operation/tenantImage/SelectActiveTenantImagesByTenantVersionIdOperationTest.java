package com.omgservers.service.module.tenant.impl.operation.tenantImage;

import com.omgservers.service.module.tenant.impl.operation.tenantImage.testInterface.SelectActiveTenantImagesByTenantVersionIdOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectActiveTenantImagesByTenantVersionIdOperationTest extends Assertions {

    @Inject
    SelectActiveTenantImagesByTenantVersionIdOperationTestInterface selectActiveTenantImagesByTenantVersionIdOperation;

    @Inject
    TestDataFactory testDataFactory;

    TestDataFactory.DefaultTestData testData;

    @BeforeEach
    void beforeEach() {
        testData = testDataFactory.createDefaultTestData();
    }

    @Test
    void givenTenantImages_whenExecute_thenSelected() {
        final var tenantVersion2 = testDataFactory.getTenantTestDataFactory()
                .createTenantVersion(testData.getTenantProject());
        final var tenantImage21 =
                testDataFactory.getTenantTestDataFactory().createTenantImage(tenantVersion2);
        final var tenantImage22 =
                testDataFactory.getTenantTestDataFactory().createTenantImage(tenantVersion2);

        final var tenantImages = selectActiveTenantImagesByTenantVersionIdOperation.execute(
                testData.getTenant().getId(),
                tenantVersion2.getId());
        assertEquals(2, tenantImages.size());
        assertTrue(tenantImages.contains(tenantImage21));
        assertTrue(tenantImages.contains(tenantImage22));
    }
}