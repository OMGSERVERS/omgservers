package com.omgservers.service.module.tenant.impl.operation;

import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.factory.tenant.TenantMatchmakerRequestModelFactory;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertVersionMatchmakerRequestOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertVersionOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Slf4j
@QuarkusTest
class UpsertTenantMatchmakerRequestOperationTest extends Assertions {

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    UpsertStageOperationTestInterface upsertStageOperation;

    @Inject
    UpsertVersionOperationTestInterface upsertVersionOperation;

    @Inject
    UpsertVersionMatchmakerRequestOperationTestInterface upsertVersionMatchmakerRequestOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    TenantProjectModelFactory tenantProjectModelFactory;

    @Inject
    TenantStageModelFactory tenantStageModelFactory;

    @Inject
    TenantVersionModelFactory tenantVersionModelFactory;

    @Inject
    TenantMatchmakerRequestModelFactory tenantMatchmakerRequestModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenVersionMatchmakerRequest_whenExecute_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = tenantStageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var version = tenantVersionModelFactory.create(tenant.getId(),
                stage.getId(),
                TenantVersionConfigDto.create(),
                Base64.getEncoder().encodeToString("archive".getBytes(StandardCharsets.UTF_8)));
        upsertVersionOperation.upsertVersion(shard, version);

        final var versionMatchmakerRequest = tenantMatchmakerRequestModelFactory.create(tenant.getId(),
                version.getId());
        final var changeContext = upsertVersionMatchmakerRequestOperation.upsertVersionMatchmakerRequest(shard,
                versionMatchmakerRequest);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_MATCHMAKER_REQUEST_CREATED));
    }

    @Test
    void givenVersionMatchmakerRequest_whenExecute_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = tenantStageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var version = tenantVersionModelFactory.create(tenant.getId(),
                stage.getId(),
                TenantVersionConfigDto.create(),
                Base64.getEncoder().encodeToString("archive".getBytes(StandardCharsets.UTF_8)));
        upsertVersionOperation.upsertVersion(shard, version);
        final var versionMatchmakerRequest = tenantMatchmakerRequestModelFactory.create(tenant.getId(),
                version.getId());
        upsertVersionMatchmakerRequestOperation.upsertVersionMatchmakerRequest(shard, versionMatchmakerRequest);

        final var changeContext = upsertVersionMatchmakerRequestOperation.upsertVersionMatchmakerRequest(shard,
                versionMatchmakerRequest);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_MATCHMAKER_REQUEST_CREATED));
    }

    @Test
    void givenUnknownIds_whenExecute_thenException() {
        final var shard = 0;
        final var versionMatchmakerRequest = tenantMatchmakerRequestModelFactory.create(tenantId(), versionId());
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertVersionMatchmakerRequestOperation.upsertVersionMatchmakerRequest(shard,
                        versionMatchmakerRequest));
    }

    @Test
    void givenVersionMatchmakerRequest_whenExecute_thenIdempotencyViolation() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = tenantStageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var version = tenantVersionModelFactory.create(tenant.getId(),
                stage.getId(),
                TenantVersionConfigDto.create(),
                Base64.getEncoder().encodeToString("archive".getBytes(StandardCharsets.UTF_8)));
        upsertVersionOperation.upsertVersion(shard, version);
        final var versionMatchmakerRequest1 = tenantMatchmakerRequestModelFactory.create(tenant.getId(),
                version.getId());
        upsertVersionMatchmakerRequestOperation.upsertVersionMatchmakerRequest(shard, versionMatchmakerRequest1);

        final var versionMatchmakerRequest2 = tenantMatchmakerRequestModelFactory.create(tenant.getId(),
                version.getId(),
                versionMatchmakerRequest1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertVersionMatchmakerRequestOperation.upsertVersionMatchmakerRequest(shard,
                        versionMatchmakerRequest2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }
}