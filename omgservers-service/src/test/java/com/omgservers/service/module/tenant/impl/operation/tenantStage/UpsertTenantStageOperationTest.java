package com.omgservers.service.module.tenant.impl.operation.tenantStage;

import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.tenantStage.testInterface.UpsertTenantStageOperationTestInterface;
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
class UpsertTenantStageOperationTest extends Assertions {

    @Inject
    UpsertTenantStageOperationTestInterface upsertTenantStageOperation;

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
    void givenTenantStage_whenExecute_thenInserted() {
        final var tenantStage = testData.getTenantStage();
        tenantStage.setId(generateIdOperation.generateId());
        tenantStage.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertTenantStageOperation.execute(tenantStage);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_STAGE_CREATED));
    }

    @Test
    void givenTenantStage_whenExecute_thenUpdated() {
        final var tenantStage = testData.getTenantStage();

        final var changeContext = upsertTenantStageOperation.execute(tenantStage);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_STAGE_CREATED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantStage = testData.getTenantStage();
        tenantStage.setId(generateIdOperation.generateId());
        tenantStage.setTenantId(generateIdOperation.generateId());
        tenantStage.setProjectId(generateIdOperation.generateId());
        tenantStage.setIdempotencyKey(generateIdOperation.generateStringId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertTenantStageOperation.execute(tenantStage));
    }

    @Test
    void givenStage_whenExecute_thenIdempotencyViolation() {
        final var tenantStage = testData.getTenantStage();
        tenantStage.setId(generateIdOperation.generateId());
        tenantStage.setTenantId(generateIdOperation.generateId());
        tenantStage.setProjectId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTenantStageOperation.execute(tenantStage));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}