package com.omgservers.service.module.tenant.impl.operation.tenantPermission;

import com.omgservers.BaseTestClass;
import com.omgservers.service.module.tenant.impl.operation.tenantPermission.testInterface.VerifyTenantPermissionExistsOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class VerifyTenantPermissionExistsOperationTest extends BaseTestClass {

    @Inject
    VerifyTenantPermissionExistsOperationTestInterface verifyTenantPermissionExistsOperation;

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
    void givenTenantPermission_whenExecute_thenTrue() {
        final var tenantPermission = testData.getTenantProjectManagerPermission();

        assertTrue(verifyTenantPermissionExistsOperation.execute(
                tenantPermission.getTenantId(),
                tenantPermission.getUserId(),
                tenantPermission.getPermission()));
    }

    @Test
    void givenUnknownIds_whenExecute_thenFalse() {
        final var tenantPermission = testData.getTenantProjectManagerPermission();
        tenantPermission.setId(generateIdOperation.generateId());
        tenantPermission.setTenantId(generateIdOperation.generateId());
        tenantPermission.setUserId(generateIdOperation.generateId());

        assertFalse(verifyTenantPermissionExistsOperation.execute(
                tenantPermission.getTenantId(),
                tenantPermission.getUserId(),
                tenantPermission.getPermission()));
    }
}