package com.omgservers.service.shard.tenant.operation.tenantImage;

import com.omgservers.BaseTestClass;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.shard.tenant.operation.tenantImage.testInterface.UpsertTenantImageOperationTestInterface;
import com.omgservers.service.operation.server.GenerateIdOperation;
import com.omgservers.testDataFactory.TestDataFactory;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertTenantImageOperationTest extends BaseTestClass {

    @Inject
    UpsertTenantImageOperationTestInterface upsertTenantImageOperation;

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
    void givenTenantImage_whenExecute_thenInserted() {
        final var tenantImage = testData.getTenantImage();
        tenantImage.setId(generateIdOperation.generateId());
        tenantImage.setIdempotencyKey(generateIdOperation.generateStringId());

        final var changeContext = upsertTenantImageOperation.execute(
                tenantImage);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_IMAGE_CREATED));
    }

    @Test
    void givenTenantImage_whenExecute_thenUpdated() {
        final var tenantImage = testData.getTenantImage();

        final var changeContext = upsertTenantImageOperation.execute(tenantImage);

        assertFalse(changeContext.getResult());
        assertTrue(changeContext.getChangeEvents().isEmpty());
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var tenantImage = testData.getTenantImage();
        tenantImage.setId(generateIdOperation.generateId());
        tenantImage.setIdempotencyKey(generateIdOperation.generateStringId());
        tenantImage.setTenantId(generateIdOperation.generateId());
        tenantImage.setVersionId(generateIdOperation.generateId());

        assertThrows(ServerSideBadRequestException.class, () -> upsertTenantImageOperation.execute(
                tenantImage));
    }

    @Test
    void givenTenantImage_whenExecute_thenIdempotencyViolation() {
        final var tenantImage = testData.getTenantImage();
        tenantImage.setId(generateIdOperation.generateId());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertTenantImageOperation.execute(tenantImage));

        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }
}