package com.omgservers.service.shard.tenant.operation.tenantVersion;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.shard.tenant.operation.tenantVersion.testInterface.DeleteTenantVersionOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteTenantVersionOperationTest extends BaseTestClass {

    @Inject
    DeleteTenantVersionOperationTestInterface deleteTenantVersionOperation;

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
    void givenVersion_whenExecute_thenDeleted() {
        final var tenantVersion = testData.getTenantVersion();

        final var changeContext = deleteTenantVersionOperation
                .execute(tenantVersion.getTenantId(), tenantVersion.getId());
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_VERSION_DELETED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenSkip() {
        final var changeContext = deleteTenantVersionOperation.execute(
                generateIdOperation.generateId(),
                generateIdOperation.generateId());
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_VERSION_DELETED));
    }
}