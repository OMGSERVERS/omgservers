package com.omgservers.service.shard.tenant.operation.tenantStageCommand;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandQualifierEnum;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.service.shard.tenant.operation.tenantStageCommand.testInterface.SelectActiveTenantStageCommandsByTenantStageIdOperationTestInterface;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectActiveTenantStageCommandsByTenantStageIdOperationTest extends BaseTestClass {

    @Inject
    SelectActiveTenantStageCommandsByTenantStageIdOperationTestInterface
            selectActiveTenantStageCommandsByTenantStageIdOperation;

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
    void givenTenantStageCommands_whenExecute_thenSelected() {
        final var tenantStage = testData.getTenantStage();

        final var tenantStageCommand1 = testDataFactory.getTenantTestDataFactory()
                .createTenantStageCommand(tenantStage, testData.getDeployment(),
                        TenantStageCommandQualifierEnum.OPEN_DEPLOYMENT);

        final var tenantStageCommand2 = testDataFactory.getTenantTestDataFactory()
                .createTenantStageCommand(tenantStage, testData.getDeployment(),
                        TenantStageCommandQualifierEnum.OPEN_DEPLOYMENT);

        final var tenantStageCommands = selectActiveTenantStageCommandsByTenantStageIdOperation.execute(
                tenantStage.getTenantId(),
                tenantStage.getId());

        assertTrue(tenantStageCommands.contains(tenantStageCommand1));
        assertTrue(tenantStageCommands.contains(tenantStageCommand2));
    }

    @Test
    void givenUnknownIds_whenExecute_thenEmptyList() {
        final var tenantStageCommands = selectActiveTenantStageCommandsByTenantStageIdOperation.execute(
                generateIdOperation.generateId(),
                generateIdOperation.generateId());

        assertTrue(tenantStageCommands.isEmpty());
    }
}