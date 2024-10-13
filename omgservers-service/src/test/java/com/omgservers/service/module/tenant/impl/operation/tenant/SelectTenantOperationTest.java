package com.omgservers.service.module.tenant.impl.operation.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.module.tenant.impl.operation.tenant.testInterface.SelectTenantOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectTenantOperationTest extends BaseTestClass {

    @Inject
    SelectTenantOperationTestInterface selectTenantOperation;

    @Inject
    TestDataFactory testDataFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenTenant_whenExecute_thenSelected() {
        final var testData = testDataFactory.createDefaultTestData();

        final var tenant = selectTenantOperation.selectTenant(testData.getTenant().getId());
        assertEquals(testData.getTenant(), tenant);
    }

    @Test
    void givenUnknownId_whenExecute_thenException() {
        assertThrows(ServerSideNotFoundException.class, () -> selectTenantOperation
                .selectTenant(generateIdOperation.generateId()));
    }
}