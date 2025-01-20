package com.omgservers.service.shard.tenant.impl.operation.tenantImage;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.tenant.impl.operation.tenantImage.testInterface.SelectActiveTenantImagesByTenantIdOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectActiveTenantImagesByTenantIdOperationTest extends BaseTestClass {

    @Inject
    SelectActiveTenantImagesByTenantIdOperationTestInterface selectActiveTenantImagesByTenantIdOperation;

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
    void givenTenantImages_whenExecute_thenSelected() {
        final var tenantVersion1 = testData.getTenantVersion();
        final var tenantImage11 = testData.getTenantImage();

        final var tenantVersion2 = testDataFactory.getTenantTestDataFactory()
                .createTenantVersion(testData.getTenantProject());
        final var tenantImage21 =
                testDataFactory.getTenantTestDataFactory().createTenantImage(tenantVersion2);
        final var tenantImage22 =
                testDataFactory.getTenantTestDataFactory().createTenantImage(tenantVersion2);

        final var tenantImages = selectActiveTenantImagesByTenantIdOperation.execute(
                testData.getTenant().getId());
        assertEquals(3, tenantImages.size());
        assertTrue(tenantImages.contains(tenantImage11));
        assertTrue(tenantImages.contains(tenantImage21));
        assertTrue(tenantImages.contains(tenantImage22));
    }
}