package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.tenant.ProjectModelFactory;
import com.omgservers.service.factory.tenant.StageModelFactory;
import com.omgservers.service.factory.tenant.TenantModelFactory;
import com.omgservers.service.factory.tenant.VersionLobbyRefModelFactory;
import com.omgservers.service.factory.tenant.VersionModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionLobbyRefOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertVersionImageRefOperationTest extends Assertions {

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    UpsertStageOperationTestInterface upsertStageOperation;

    @Inject
    UpsertVersionOperationTestInterface upsertVersionOperation;

    @Inject
    UpsertVersionLobbyRefOperationTestInterface upsertVersionLobbyRefOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    ProjectModelFactory projectModelFactory;

    @Inject
    StageModelFactory stageModelFactory;

    @Inject
    VersionModelFactory versionModelFactory;

    @Inject
    VersionLobbyRefModelFactory versionLobbyRefModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenVersionLobbyRef_whenUpsertVersionLobbyRef_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var version = versionModelFactory.create(tenant.getId(),
                stage.getId(),
                VersionConfigModel.create(),
                VersionSourceCodeModel.create());
        upsertVersionOperation.upsertVersion(shard, version);

        final var versionLobbyRef = versionLobbyRefModelFactory.create(tenant.getId(),
                version.getId(),
                lobbyId());
        final var changeContext = upsertVersionLobbyRefOperation.upsertVersionLobbyRef(shard,
                versionLobbyRef);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.VERSION_LOBBY_REF_CREATED));
    }

    @Test
    void givenVersionLobbyRef_whenUpsertVersionLobbyRef_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var version = versionModelFactory.create(tenant.getId(),
                stage.getId(),
                VersionConfigModel.create(),
                VersionSourceCodeModel.create());
        upsertVersionOperation.upsertVersion(shard, version);
        final var versionLobbyRef = versionLobbyRefModelFactory.create(tenant.getId(),
                version.getId(),
                lobbyId());
        upsertVersionLobbyRefOperation.upsertVersionLobbyRef(shard, versionLobbyRef);

        final var changeContext = upsertVersionLobbyRefOperation.upsertVersionLobbyRef(shard,
                versionLobbyRef);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.VERSION_LOBBY_REF_CREATED));
    }

    @Test
    void givenUnknownIds_whenUpsertVersionLobbyRef_thenException() {
        final var shard = 0;
        final var versionLobbyRef = versionLobbyRefModelFactory.create(tenantId(), versionId(), lobbyId());
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertVersionLobbyRefOperation.upsertVersionLobbyRef(shard, versionLobbyRef));
    }

    @Test
    void givenVersionLobbyRef_whenUpsertVersionLobbyRef_thenIdempotencyViolation() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var version = versionModelFactory.create(tenant.getId(),
                stage.getId(),
                VersionConfigModel.create(),
                VersionSourceCodeModel.create());
        upsertVersionOperation.upsertVersion(shard, version);
        final var versionLobbyRef1 = versionLobbyRefModelFactory.create(tenant.getId(),
                version.getId(),
                lobbyId());
        upsertVersionLobbyRefOperation.upsertVersionLobbyRef(shard, versionLobbyRef1);

        final var versionLobbyRef2 = versionLobbyRefModelFactory.create(tenant.getId(),
                version.getId(),
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