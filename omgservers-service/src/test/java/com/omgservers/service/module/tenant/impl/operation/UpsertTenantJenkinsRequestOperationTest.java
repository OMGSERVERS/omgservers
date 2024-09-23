package com.omgservers.service.module.tenant.impl.operation;

import com.omgservers.service.event.EventQualifierEnum;
import com.omgservers.schema.model.tenantVersion.TenantVersionConfigDto;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.TenantDeploymentModelFactory;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.factory.tenant.TenantLobbyRequestModelFactory;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantDeploymentOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertVersionLobbyRequestOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantVersionOperationTestInterface;
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
class UpsertTenantJenkinsRequestOperationTest extends Assertions {

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    UpsertStageOperationTestInterface upsertStageOperation;

    @Inject
    UpsertTenantVersionOperationTestInterface upsertVersionOperation;

    @Inject
    UpsertTenantDeploymentOperationTestInterface upsertTenantDeploymentOperation;

    @Inject
    UpsertVersionLobbyRequestOperationTestInterface upsertVersionLobbyRequestOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    TenantProjectModelFactory tenantProjectModelFactory;

    @Inject
    TenantStageModelFactory tenantStageModelFactory;

    @Inject
    TenantVersionModelFactory tenantVersionModelFactory;

    @Inject
    TenantDeploymentModelFactory tenantDeploymentModelFactory;

    @Inject
    TenantLobbyRequestModelFactory tenantLobbyRequestModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenVersionLobbyRequest_whenUpsertVersionLobbyRequest_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = tenantStageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var version = tenantVersionModelFactory.create(tenant.getId(),
                project.getId(),
                TenantVersionConfigDto.create(),
                Base64.getEncoder().encodeToString("archive".getBytes(StandardCharsets.UTF_8)));
        upsertVersionOperation.upsertTenantVersion(shard, version);
        final var tenantDeployment = tenantDeploymentModelFactory.create(tenant.getId(),
                stage.getId(),
                version.getId());
        upsertTenantDeploymentOperation.upsertTenantDeployment(shard, tenantDeployment);

        final var versionLobbyRequest = tenantLobbyRequestModelFactory.create(tenant.getId(),
                tenantDeployment.getId());
        final var changeContext = upsertVersionLobbyRequestOperation.upsertVersionLobbyRequest(shard,
                versionLobbyRequest);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_LOBBY_REQUEST_CREATED));
    }

    @Test
    void givenVersionLobbyRequest_whenUpsertVersionLobbyRequest_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = tenantStageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var version = tenantVersionModelFactory.create(tenant.getId(),
                project.getId(),
                TenantVersionConfigDto.create(),
                Base64.getEncoder().encodeToString("archive".getBytes(StandardCharsets.UTF_8)));
        upsertVersionOperation.upsertTenantVersion(shard, version);
        final var tenantDeployment = tenantDeploymentModelFactory.create(tenant.getId(),
                stage.getId(),
                version.getId());
        upsertTenantDeploymentOperation.upsertTenantDeployment(shard, tenantDeployment);
        final var versionLobbyRequest = tenantLobbyRequestModelFactory.create(tenant.getId(),
                tenantDeployment.getId());
        upsertVersionLobbyRequestOperation.upsertVersionLobbyRequest(shard, versionLobbyRequest);

        final var changeContext = upsertVersionLobbyRequestOperation.upsertVersionLobbyRequest(shard,
                versionLobbyRequest);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_LOBBY_REQUEST_CREATED));
    }

    @Test
    void givenUnknownIds_whenUpsertVersionLobbyRequest_thenException() {
        final var shard = 0;
        final var versionLobbyRequest = tenantLobbyRequestModelFactory.create(tenantId(), versionId());
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertVersionLobbyRequestOperation.upsertVersionLobbyRequest(shard, versionLobbyRequest));
    }

    @Test
    void givenVersionLobbyRequest_whenUpsertVersionLobbyRequest_thenIdempotencyViolation() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = tenantProjectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = tenantStageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var version = tenantVersionModelFactory.create(tenant.getId(),
                project.getId(),
                TenantVersionConfigDto.create(),
                Base64.getEncoder().encodeToString("archive".getBytes(StandardCharsets.UTF_8)));
        upsertVersionOperation.upsertTenantVersion(shard, version);
        final var tenantDeployment = tenantDeploymentModelFactory.create(tenant.getId(),
                stage.getId(),
                version.getId());
        upsertTenantDeploymentOperation.upsertTenantDeployment(shard, tenantDeployment);
        final var versionLobbyRequest1 = tenantLobbyRequestModelFactory.create(tenant.getId(),
                tenantDeployment.getId());
        upsertVersionLobbyRequestOperation.upsertVersionLobbyRequest(shard, versionLobbyRequest1);

        final var versionLobbyRequest2 = tenantLobbyRequestModelFactory.create(tenant.getId(),
                tenantDeployment.getId(),
                versionLobbyRequest1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertVersionLobbyRequestOperation.upsertVersionLobbyRequest(shard, versionLobbyRequest2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }
}