package com.omgservers.service.shard.tenant.operation.tenantStageCommand;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandQualifierEnum;
import com.omgservers.schema.model.tenantStageCommand.body.OpenDeploymentTenantStageCommandBodyDto;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.shard.tenant.operation.tenantStageCommand.testInterface.UpsertTenantStageCommandOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantStageCommandOperationTest extends BaseTestClass {

    @Inject
    UpsertTenantStageCommandOperationTestInterface upsertTenantStageCommandOperation;

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
    void givenTenantStageCommand_whenExecute_thenInserted() {
        final var tenantStageCommand = testData.getOpenDeploymentTenantStageCommand();
        tenantStageCommand.setId(generateIdOperation.generateId());
        tenantStageCommand.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertTenantStageCommandOperation.execute(
                tenantStageCommand);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenTenantStageCommand_whenExecute_thenUpdated() {
        final var tenantStageCommand = testData.getOpenDeploymentTenantStageCommand();

        final var changeContext = upsertTenantStageCommandOperation.execute(tenantStageCommand);

        assertFalse(changeContext.getResult());
        assertTrue(changeContext.getChangeEvents().isEmpty());
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantStageCommand = testData.getOpenDeploymentTenantStageCommand();
        tenantStageCommand.setId(generateIdOperation.generateId());
        tenantStageCommand.setIdempotencyKey(generateIdOperation.generateStringId());
        tenantStageCommand.setTenantId(generateIdOperation.generateId());
        tenantStageCommand.setStageId(generateIdOperation.generateId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertTenantStageCommandOperation.execute(
                tenantStageCommand));
    }

    @Test
    void givenTenantStageCommand_whenExecute_thenIdempotencyViolation() {
        final var tenantStageCommand = testData.getOpenDeploymentTenantStageCommand();
        tenantStageCommand.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTenantStageCommandOperation.execute(tenantStageCommand));

        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}