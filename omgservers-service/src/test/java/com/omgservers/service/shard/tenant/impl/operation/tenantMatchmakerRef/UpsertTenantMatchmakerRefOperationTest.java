package com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerRef;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerRef.testInterface.UpsertTenantMatchmakerRefOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantMatchmakerRefOperationTest extends BaseTestClass {

    @Inject
    UpsertTenantMatchmakerRefOperationTestInterface upsertTenantMatchmakerRefOperation;

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
    void givenVersionMatchmakerRef_whenExecute_thenInserted() {
        final var tenantMatchmakerRef = testData.getTenantMatchmakerRef();
        tenantMatchmakerRef.setId(generateIdOperation.generateId());
        tenantMatchmakerRef.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertTenantMatchmakerRefOperation
                .execute(tenantMatchmakerRef);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_MATCHMAKER_REF_CREATED));
    }

    @Test
    void givenVersionMatchmakerRef_whenExecute_thenUpdated() {
        final var tenantMatchmakerRef = testData.getTenantMatchmakerRef();

        final var changeContext = upsertTenantMatchmakerRefOperation
                .execute(tenantMatchmakerRef);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_MATCHMAKER_REF_CREATED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantMatchmakerRef = testData.getTenantMatchmakerRef();
        tenantMatchmakerRef.setId(generateIdOperation.generateId());
        tenantMatchmakerRef.setTenantId(generateIdOperation.generateId());
        tenantMatchmakerRef.setDeploymentId(generateIdOperation.generateId());
        tenantMatchmakerRef.setIdempotencyKey(generateIdOperation.generateStringId());

        assertThrows(ServerSideBadRequestException.class, () ->
                upsertTenantMatchmakerRefOperation.execute(tenantMatchmakerRef));
    }

    @Test
    void givenVersionMatchmakerRef_whenExecute_thenIdempotencyViolation() {
        final var tenantMatchmakerRef = testData.getTenantMatchmakerRef();
        tenantMatchmakerRef.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTenantMatchmakerRefOperation.execute(tenantMatchmakerRef));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}