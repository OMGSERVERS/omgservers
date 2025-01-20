package com.omgservers.service.shard.tenant.impl.operation.tenantStagePermission;

import com.omgservers.BaseTestClass;
import com.omgservers.service.shard.tenant.impl.operation.tenantStagePermission.testInterface.VerifyTenantStagePermissionExistsOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class VerifyTenantStagePermissionExistsOperationTest extends BaseTestClass {

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
        final var tenantStagePermission = testData.getTenantStageDeploymentManagerPermission();
        tenantStagePermission.setId(generateIdOperation.generateId());

        assertTrue(verifyTenantStagePermissionExistsOperation.execute(
                tenantStagePermission.getTenantId(),
                tenantStagePermission.getStageId(),
                tenantStagePermission.getUserId(),
                tenantStagePermission.getPermission()));
    }

    @Test
    void givenUnknownIds_whenExecute_thenFalse() {
        final var tenantStagePermission = testData.getTenantStageDeploymentManagerPermission();
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