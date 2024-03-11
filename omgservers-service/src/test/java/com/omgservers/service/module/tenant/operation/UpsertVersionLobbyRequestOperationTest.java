package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.event.EventQualifierEnum;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.ProjectModelFactory;
import com.omgservers.service.factory.StageModelFactory;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.factory.VersionLobbyRequestModelFactory;
import com.omgservers.service.factory.VersionModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionLobbyRequestOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertVersionLobbyRequestOperationTest extends Assertions {

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    UpsertStageOperationTestInterface upsertStageOperation;

    @Inject
    UpsertVersionOperationTestInterface upsertVersionOperation;

    @Inject
    UpsertVersionLobbyRequestOperationTestInterface upsertVersionLobbyRequestOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    ProjectModelFactory projectModelFactory;

    @Inject
    StageModelFactory stageModelFactory;

    @Inject
    VersionModelFactory versionModelFactory;

    @Inject
    VersionLobbyRequestModelFactory versionLobbyRequestModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenVersionLobbyRequest_whenUpsertVersionLobbyRequest_thenInserted() {
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

        final var versionLobbyRequest = versionLobbyRequestModelFactory.create(tenant.getId(),
                version.getId());
        final var changeContext = upsertVersionLobbyRequestOperation.upsertVersionLobbyRequest(shard,
                versionLobbyRequest);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.VERSION_LOBBY_REQUEST_CREATED));
    }

    @Test
    void givenVersionLobbyRequest_whenUpsertVersionLobbyRequest_thenUpdated() {
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
        final var versionLobbyRequest = versionLobbyRequestModelFactory.create(tenant.getId(), version.getId());
        upsertVersionLobbyRequestOperation.upsertVersionLobbyRequest(shard, versionLobbyRequest);

        final var changeContext = upsertVersionLobbyRequestOperation.upsertVersionLobbyRequest(shard,
                versionLobbyRequest);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.VERSION_LOBBY_REQUEST_CREATED));
    }

    @Test
    void givenUnknownIds_whenUpsertVersionLobbyRequest_thenException() {
        final var shard = 0;
        final var versionLobbyRequest = versionLobbyRequestModelFactory.create(tenantId(), versionId());
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertVersionLobbyRequestOperation.upsertVersionLobbyRequest(shard, versionLobbyRequest));
    }

    @Test
    void givenVersionLobbyRequest_whenUpsertVersionLobbyRequest_thenIdempotencyViolation() {
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
        final var versionLobbyRequest1 = versionLobbyRequestModelFactory.create(tenant.getId(), version.getId());
        upsertVersionLobbyRequestOperation.upsertVersionLobbyRequest(shard, versionLobbyRequest1);

        final var versionLobbyRequest2 = versionLobbyRequestModelFactory.create(tenant.getId(), version.getId(),
                versionLobbyRequest1.getIdempotencyKey());

        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertVersionLobbyRequestOperation.upsertVersionLobbyRequest(shard, versionLobbyRequest2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }
}