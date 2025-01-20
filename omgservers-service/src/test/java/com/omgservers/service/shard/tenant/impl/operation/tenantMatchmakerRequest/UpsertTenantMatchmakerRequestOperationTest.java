package com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerRequest;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerRequest.testInterface.UpsertTenantMatchmakerRequestOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantMatchmakerRequestOperationTest extends BaseTestClass {

    @Inject
    UpsertTenantMatchmakerRequestOperationTestInterface upsertVersionMatchmakerRequestOperation;

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
    void givenTenantMatchmakerRequest_whenExecute_thenInserted() {
        final var tenantMatchmakerRequest = testData.getTenantMatchmakerRequest();
        tenantMatchmakerRequest.setId(generateIdOperation.generateId());
        tenantMatchmakerRequest.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertVersionMatchmakerRequestOperation
                .execute(tenantMatchmakerRequest);

        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_MATCHMAKER_REQUEST_CREATED));
    }

    @Test
    void givenTenantMatchmakerRequest_whenExecute_thenUpdated() {
        final var tenantMatchmakerRequest = testData.getTenantMatchmakerRequest();

        final var changeContext = upsertVersionMatchmakerRequestOperation
                .execute(tenantMatchmakerRequest);

        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_MATCHMAKER_REQUEST_CREATED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantMatchmakerRequest = testData.getTenantMatchmakerRequest();
        tenantMatchmakerRequest.setId(generateIdOperation.generateId());
        tenantMatchmakerRequest.setTenantId(generateIdOperation.generateId());
        tenantMatchmakerRequest.setDeploymentId(generateIdOperation.generateId());
        tenantMatchmakerRequest.setIdempotencyKey(generateIdOperation.generateStringId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertVersionMatchmakerRequestOperation
                .execute(tenantMatchmakerRequest));
    }

    @Test
    void givenTenantMatchmakerRequest_whenExecute_thenIdempotencyViolation() {
        final var tenantMatchmakerRequest = testData.getTenantMatchmakerRequest();
        tenantMatchmakerRequest.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertVersionMatchmakerRequestOperation.execute(tenantMatchmakerRequest));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}