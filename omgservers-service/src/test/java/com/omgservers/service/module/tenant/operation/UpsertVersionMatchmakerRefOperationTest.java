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
import com.omgservers.service.factory.tenant.VersionMatchmakerRefModelFactory;
import com.omgservers.service.factory.tenant.VersionModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionMatchmakerRefOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertVersionMatchmakerRefOperationTest extends Assertions {

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    UpsertStageOperationTestInterface upsertStageOperation;

    @Inject
    UpsertVersionOperationTestInterface upsertVersionOperation;

    @Inject
    UpsertVersionMatchmakerRefOperationTestInterface upsertVersionMatchmakerRefOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    ProjectModelFactory projectModelFactory;

    @Inject
    StageModelFactory stageModelFactory;

    @Inject
    VersionModelFactory versionModelFactory;

    @Inject
    VersionMatchmakerRefModelFactory VersionMatchmakerRefModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenVersionMatchmakerRef_whenUpsertVersionMatchmakerRef_thenInserted() {
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

        final var VersionMatchmakerRef = VersionMatchmakerRefModelFactory.create(tenant.getId(),
                version.getId(),
                matchmakerId());
        final var changeContext = upsertVersionMatchmakerRefOperation.upsertVersionMatchmakerRef(shard,
                VersionMatchmakerRef);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.VERSION_MATCHMAKER_REF_CREATED));
    }

    @Test
    void givenVersionMatchmakerRef_whenUpsertVersionMatchmakerRef_thenUpdated() {
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
        final var VersionMatchmakerRef = VersionMatchmakerRefModelFactory.create(tenant.getId(),
                version.getId(),
                matchmakerId());
        upsertVersionMatchmakerRefOperation.upsertVersionMatchmakerRef(shard, VersionMatchmakerRef);

        final var changeContext = upsertVersionMatchmakerRefOperation.upsertVersionMatchmakerRef(shard,
                VersionMatchmakerRef);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.VERSION_MATCHMAKER_REF_CREATED));
    }

    @Test
    void givenUnknownIds_whenUpsertVersionMatchmakerRef_thenException() {
        final var shard = 0;
        final var VersionMatchmakerRef = VersionMatchmakerRefModelFactory.create(tenantId(),
                versionId(),
                matchmakerId());
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertVersionMatchmakerRefOperation.upsertVersionMatchmakerRef(shard,
                        VersionMatchmakerRef));
    }

    @Test
    void givenVersionMatchmakerRef_whenUpsertVersionMatchmakerRef_thenIdempotencyViolation() {
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
        final var VersionMatchmakerRef1 = VersionMatchmakerRefModelFactory.create(tenant.getId(),
                version.getId(),
                matchmakerId());
        upsertVersionMatchmakerRefOperation.upsertVersionMatchmakerRef(shard, VersionMatchmakerRef1);

        final var VersionMatchmakerRef2 = VersionMatchmakerRefModelFactory.create(tenant.getId(),
                version.getId(),
                matchmakerId(),
                VersionMatchmakerRef1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertVersionMatchmakerRefOperation.upsertVersionMatchmakerRef(shard,
                        VersionMatchmakerRef2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATED, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }

    Long matchmakerId() {
        return generateIdOperation.generateId();
    }
}