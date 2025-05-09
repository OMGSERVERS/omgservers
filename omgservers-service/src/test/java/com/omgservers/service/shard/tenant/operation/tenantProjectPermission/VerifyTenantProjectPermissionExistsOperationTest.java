package com.omgservers.service.shard.tenant.operation.tenantProjectPermission;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.tenantProjectPermission.TenantProjectPermissionQualifierEnum;
import com.omgservers.service.shard.tenant.operation.tenantProjectPermission.testInterface.VerifyTenantProjectPermissionExistsOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class VerifyTenantProjectPermissionExistsOperationTest extends BaseTestClass {

    @Inject
    VerifyTenantProjectPermissionExistsOperationTestInterface verifyTenantProjectPermissionExistsOperation;

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
    void givenTenantProjectPermission_whenExecute_thenTrue() {
        final var tenantProjectPermission = testData.getTenantProjectStageManagerPermission();

        assertTrue(verifyTenantProjectPermissionExistsOperation.execute(
                tenantProjectPermission.getTenantId(),
                tenantProjectPermission.getProjectId(),
                tenantProjectPermission.getUserId(),
                tenantProjectPermission.getPermission()));
    }

    @Test
    void givenUnknownIds_whenExecute_thenFalse() {

        assertFalse(verifyTenantProjectPermissionExistsOperation.execute(
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                generateIdOperation.generateId(),
                TenantProjectPermissionQualifierEnum.STAGE_MANAGER));
    }
}