package com.omgservers.service.shard.tenant.impl.operation.tenantImage;

import com.omgservers.BaseTestClass;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.tenant.impl.operation.tenantImage.testInterface.SelectTenantImageOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectTenantImageOperationTest extends BaseTestClass {

    @Inject
    SelectTenantImageOperationTestInterface selectTenantImageOperation;

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
    void givenTenantImage_whenExecute_thenSelected() {
        final var tenantImage = selectTenantImageOperation.execute(
                testData.getTenantImage().getTenantId(),
                testData.getTenantImage().getId());
        assertEquals(testData.getTenantImage(), tenantImage);
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        assertThrows(ServerSideNotFoundException.class, () -> selectTenantImageOperation.execute(
                generateIdOperation.generateId(),
                generateIdOperation.generateId()));
    }
}