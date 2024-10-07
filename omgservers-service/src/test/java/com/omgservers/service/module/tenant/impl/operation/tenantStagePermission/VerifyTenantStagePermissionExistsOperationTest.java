package com.omgservers.service.module.tenant.impl.operation.tenantStagePermission;

import com.omgservers.service.module.tenant.impl.operation.tenantStagePermission.testInterface.VerifyTenantStagePermissionExistsOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class VerifyTenantStagePermissionExistsOperationTest extends Assertions {

    @Inject
    VerifyTenantStagePermissionExistsOperationTestInterface verifyTenantStagePermissionExistsOperation;

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
    void givenStagePermission_whenExecute_thenTrue() {
        final var tenantStagePermission = testData.getTenantStageDeploymentManagementPermission();
        tenantStagePermission.setId(generateIdOperation.generateId());

        assertTrue(verifyTenantStagePermissionExistsOperation.execute(
                tenantStagePermission.getTenantId(),
                tenantStagePermission.getStageId(),
                tenantStagePermission.getUserId(),
                tenantStagePermission.getPermission()));
    }

    @Test
    void givenUnknownIds_whenExecute_thenFalse() {
        final var tenantStagePermission = testData.getTenantStageDeploymentManagementPermission();
        tenantStagePermission.setTenantId(generateIdOperation.generateId());
        tenantStagePermission.setStageId(generateIdOperation.generateId());
        tenantStagePermission.setUserId(generateIdOperation.generateId());

        assertFalse(verifyTenantStagePermissionExistsOperation.execute(
                tenantStagePermission.getTenantId(),
                tenantStagePermission.getStageId(),
                tenantStagePermission.getUserId(),
                tenantStagePermission.getPermission()));
    }
}