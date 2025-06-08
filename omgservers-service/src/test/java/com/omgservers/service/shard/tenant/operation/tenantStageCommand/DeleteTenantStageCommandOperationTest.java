package com.omgservers.service.shard.tenant.operation.tenantStageCommand;

import com.omgservers.BaseTestClass;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.shard.tenant.operation.tenantStageCommand.testInterface.DeleteTenantStageCommandOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class DeleteTenantStageCommandOperationTest extends BaseTestClass {

    @Inject
    DeleteTenantStageCommandOperationTestInterface deleteTenantStageCommandOperation;

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
    void givenTenantStageCommand_whenExecute_thenDeleted() {
        final var changeContext = deleteTenantStageCommandOperation.execute(
                testData.getOpenDeploymentTenantStageCommand().getTenantId(),
                testData.getOpenDeploymentTenantStageCommand().getId());
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenUnknownIds_whenExecute_thenFalse() {
        final var changeContext = deleteTenantStageCommandOperation.execute(
                generateIdOperation.generateId(),
                generateIdOperation.generateId());
        assertFalse(changeContext.getResult());
        assertTrue(changeContext.getChangeEvents().isEmpty());
    }
}