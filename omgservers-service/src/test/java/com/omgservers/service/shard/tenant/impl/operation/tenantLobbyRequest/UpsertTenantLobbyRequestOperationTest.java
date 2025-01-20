package com.omgservers.service.shard.tenant.impl.operation.tenantLobbyRequest;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.shard.tenant.impl.operation.tenantLobbyRequest.testInterface.UpsertTenantLobbyRequestOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantLobbyRequestOperationTest extends BaseTestClass {

    @Inject
    UpsertTenantLobbyRequestOperationTestInterface upsertVersionLobbyRequestOperation;

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
    void givenVersionLobbyRequest_whenExecute_thenInserted() {
        final var tenantLobbyRequest = testData.getTenantLobbyRequest();
        tenantLobbyRequest.setId(generateIdOperation.generateId());
        tenantLobbyRequest.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertVersionLobbyRequestOperation
                .execute(tenantLobbyRequest);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_LOBBY_REQUEST_CREATED));
    }

    @Test
    void givenVersionLobbyRequest_whenUpsertVersionLobbyRequest_thenUpdated() {
        final var tenantLobbyRequest = testData.getTenantLobbyRequest();

        final var changeContext = upsertVersionLobbyRequestOperation
                .execute(tenantLobbyRequest);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_LOBBY_REQUEST_CREATED));
    }

    @Test
    void givenUnknownIds_whenUpsertVersionLobbyRequest_thenException() {
        final var tenantLobbyRequest = testData.getTenantLobbyRequest();
        tenantLobbyRequest.setId(generateIdOperation.generateId());
        tenantLobbyRequest.setTenantId(generateIdOperation.generateId());
        tenantLobbyRequest.setDeploymentId(generateIdOperation.generateId());
        tenantLobbyRequest.setIdempotencyKey(generateIdOperation.generateStringId());
        assertThrows(ServerSideBadRequestException.class, () -> upsertVersionLobbyRequestOperation
                .execute(tenantLobbyRequest));
    }

    @Test
    void givenVersionLobbyRequest_whenUpsertVersionLobbyRequest_thenIdempotencyViolation() {
        final var tenantLobbyRequest = testData.getTenantLobbyRequest();
        tenantLobbyRequest.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertVersionLobbyRequestOperation.execute(tenantLobbyRequest));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}