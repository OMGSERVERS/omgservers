package com.omgservers.service.module.tenant.impl.operation.tenantLobbyRef;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.module.tenant.impl.operation.tenantLobbyRef.testInterface.UpsertTenantLobbyRefOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantLobbyRefOperationTest extends BaseTestClass {

    @Inject
    UpsertTenantLobbyRefOperationTestInterface upsertTenantLobbyRefOperation;

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
    void givenTenantLobbyRef_whenExecute_thenInserted() {
        final var tenantLobbyRef = testData.getTenantLobbyRef();
        tenantLobbyRef.setId(generateIdOperation.generateId());
        tenantLobbyRef.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertTenantLobbyRefOperation.execute(tenantLobbyRef);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_LOBBY_REF_CREATED));
    }

    @Test
    void givenTenantLobbyRef_whenExecute_thenUpdated() {
        final var tenantLobbyRef = testData.getTenantLobbyRef();

        final var changeContext = upsertTenantLobbyRefOperation.execute(tenantLobbyRef);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_LOBBY_REF_CREATED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantLobbyRef = testData.getTenantLobbyRef();
        tenantLobbyRef.setId(generateIdOperation.generateId());
        tenantLobbyRef.setTenantId(generateIdOperation.generateId());
        tenantLobbyRef.setDeploymentId(generateIdOperation.generateId());
        tenantLobbyRef.setIdempotencyKey(generateIdOperation.generateStringId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertTenantLobbyRefOperation.execute(tenantLobbyRef));
    }

    @Test
    void givenTenantLobbyRef_whenExecute_thenIdempotencyViolation() {
        final var tenantLobbyRef = testData.getTenantLobbyRef();
        tenantLobbyRef.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTenantLobbyRefOperation.execute(tenantLobbyRef));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}