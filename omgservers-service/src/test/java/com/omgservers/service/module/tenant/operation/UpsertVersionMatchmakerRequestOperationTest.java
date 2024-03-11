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
import com.omgservers.service.factory.VersionMatchmakerRequestModelFactory;
import com.omgservers.service.factory.VersionModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionMatchmakerRequestOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertVersionMatchmakerRequestOperationTest extends Assertions {

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
    ProjectModelFactory projectModelFactory;

    @Inject
    StageModelFactory stageModelFactory;

    @Inject
    VersionModelFactory versionModelFactory;

    @Inject
    VersionMatchmakerRequestModelFactory versionMatchmakerRequestModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenVersionMatchmakerRequest_whenUpsertVersionMatchmakerRequest_thenInserted() {
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

        final var versionMatchmakerRequest = versionMatchmakerRequestModelFactory.create(tenant.getId(),
                version.getId());
        final var changeContext = upsertVersionMatchmakerRequestOperation.upsertVersionMatchmakerRequest(shard,
                versionMatchmakerRequest);
        assertTrue(changeContext.getResult());
        assertTrue(changeContext.contains(EventQualifierEnum.VERSION_MATCHMAKER_REQUEST_CREATED));
    }

    @Test
    void givenVersionMatchmakerRequest_whenUpsertVersionMatchmakerRequest_thenUpdated() {
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
        final var versionMatchmakerRequest = versionMatchmakerRequestModelFactory.create(tenant.getId(),
                version.getId());
        upsertVersionMatchmakerRequestOperation.upsertVersionMatchmakerRequest(shard, versionMatchmakerRequest);

        final var changeContext = upsertVersionMatchmakerRequestOperation.upsertVersionMatchmakerRequest(shard,
                versionMatchmakerRequest);
        assertFalse(changeContext.getResult());
        assertFalse(changeContext.contains(EventQualifierEnum.VERSION_MATCHMAKER_REQUEST_CREATED));
    }

    @Test
    void givenUnknownIds_whenUpsertVersionMatchmakerRequest_thenException() {
        final var shard = 0;
        final var versionMatchmakerRequest = versionMatchmakerRequestModelFactory.create(tenantId(), versionId());
        assertThrows(ServerSideBadRequestException.class, () ->
                upsertVersionMatchmakerRequestOperation.upsertVersionMatchmakerRequest(shard,
                        versionMatchmakerRequest));
    }

    @Test
    void givenVersionMatchmakerRequest_whenUpsertVersionMatchmakerRequest_thenIdempotencyViolation() {
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
        final var versionMatchmakerRequest1 = versionMatchmakerRequestModelFactory.create(tenant.getId(),
                version.getId());
        upsertVersionMatchmakerRequestOperation.upsertVersionMatchmakerRequest(shard, versionMatchmakerRequest1);

        final var versionMatchmakerRequest2 = versionMatchmakerRequestModelFactory.create(tenant.getId(),
                version.getId(),
                versionMatchmakerRequest1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertVersionMatchmakerRequestOperation.upsertVersionMatchmakerRequest(shard,
                        versionMatchmakerRequest2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION, exception.getQualifier());
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }

    Long versionId() {
        return generateIdOperation.generateId();
    }
}