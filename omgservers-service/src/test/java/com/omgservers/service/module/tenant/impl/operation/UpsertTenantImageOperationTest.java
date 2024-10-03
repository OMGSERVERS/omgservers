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
import com.omgservers.service.factory.tenant.TenantLobbyRefModelFactory;
import com.omgservers.service.factory.tenant.TenantVersionModelFactory;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantDeploymentOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.module.tenant.impl.operation.testInterface.UpsertVersionLobbyRefOperationTestInterface;
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
class UpsertTenantImageOperationTest extends Assertions {

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
    UpsertVersionLobbyRefOperationTestInterface upsertVersionLobbyRefOperation;

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
    TenantLobbyRefModelFactory tenantLobbyRefModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenVersionLobbyRef_whenUpsertVersionLobbyRef_thenInserted() {
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

        final var versionLobbyRef = tenantLobbyRefModelFactory.create(tenant.getId(),
                tenantDeployment.getId(),
                lobbyId());
        final var changeContext = upsertVersionLobbyRefOperation.upsertVersionLobbyRef(shard,
                versionLobbyRef);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.TENANT_LOBBY_REF_CREATED));
    }

    @Test
    void givenVersionLobbyRef_whenUpsertVersionLobbyRef_thenUpdated() {
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
        final var versionLobbyRef = tenantLobbyRefModelFactory.create(tenant.getId(),
                tenantDeployment.getId(),
                lobbyId());
        upsertVersionLobbyRefOperation.upsertVersionLobbyRef(shard, versionLobbyRef);

        final var changeContext = upsertVersionLobbyRefOperation.upsertVersionLobbyRef(shard,
                versionLobbyRef);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.TENANT_LOBBY_REF_CREATED));
    }

    @Test
    void givenUnknownIds_whenUpsertVersionLobbyRef_thenException() {
        final var shard = 0;
        final var versionLobbyRef = tenantLobbyRefModelFactory.create(tenantId(), versionId(), lobbyId());
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertVersionLobbyRefOperation.upsertVersionLobbyRef(shard, versionLobbyRef));
    }

    @Test
    void givenVersionLobbyRef_whenUpsertVersionLobbyRef_thenIdempotencyViolation() {
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
        final var versionLobbyRef1 = tenantLobbyRefModelFactory.create(tenant.getId(),
                tenantDeployment.getId(),
                lobbyId());
        upsertVersionLobbyRefOperation.upsertVersionLobbyRef(shard, versionLobbyRef1);

        final var versionLobbyRef2 = tenantLobbyRefModelFactory.create(tenant.getId(),
                project.getId(),
                lobbyId(),
                versionLobbyRef1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertVersionLobbyRefOperation.upsertVersionLobbyRef(shard, versionLobbyRef2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long lobbyId() {
        return generateIdOperation.generateId();
    }
}