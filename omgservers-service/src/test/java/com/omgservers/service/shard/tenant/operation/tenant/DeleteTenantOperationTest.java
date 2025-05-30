package com.omgservers.service.shard.tenant.operation.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.shard.tenant.operation.tenant.testInterface.DeleteTenantOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteTenantOperationTest extends BaseTestClass {

    @Inject
    DeleteTenantOperationTestInterface deleteTenantOperation;

    @Inject
    TestDataFactory testDataFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenTenant_whenDeleteTenant_thenDeleted() {
        final var testData = testDataFactory.createDefaultTestData();

        final var changeContext = deleteTenantOperation
                .deleteTenant(testData.getTenant().getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_DELETED));
    }

    @Test
    void givenUnknownIds_whenDeleteTenant_thenFalse() {
        final var changeContext = deleteTenantOperation
                .deleteTenant(generateIdOperation.generateId());
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_DELETED));
    }
}