package com.omgservers.service.shard.tenant.impl.operation.tenant;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.tenant.impl.operation.tenant.testInterface.VerifyTenantExistsOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class VerifyTenantExistsOperationTest extends BaseTestClass {

    @Inject
    VerifyTenantExistsOperationTestInterface verifyTenantExistsOperation;

    @Inject
    TestDataFactory testDataFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenTenant_whenExecute_thenTrue() {
        final var testData = testDataFactory.createDefaultTestData();
        final var exists = verifyTenantExistsOperation.execute(testData.getTenant().getId());
        assertTrue(exists);
    }

    @Test
    void givenUnknownId_whenExecute_thenFalse() {
        final var exists = verifyTenantExistsOperation.execute(generateIdOperation.generateId());
        assertFalse(exists);
    }

}