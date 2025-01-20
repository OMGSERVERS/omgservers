package com.omgservers.service.shard.tenant.impl.operation.tenantVersion;

import com.omgservers.BaseTestClass;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.shard.tenant.impl.operation.tenantVersion.testInterface.SelectTenantVersionOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectTenantVersionOperationTest extends BaseTestClass {

    @Inject
    SelectTenantVersionOperationTestInterface selectVersionOperation;

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
    void givenTenantVersion_whenExecute_thenSelected() {
        final var tenantVersion = selectVersionOperation.execute(
                testData.getTenantVersion().getTenantId(),
                testData.getTenantVersion().getId());
        assertEquals(testData.getTenantVersion(), tenantVersion);
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        assertThrows(ServerSideNotFoundException.class, () -> selectVersionOperation.execute(
                generateIdOperation.generateId(),
                generateIdOperation.generateId()));
    }
}