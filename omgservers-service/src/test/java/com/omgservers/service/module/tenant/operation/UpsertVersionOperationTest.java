package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.service.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideConflictException;
import com.omgservers.service.factory.ProjectModelFactory;
import com.omgservers.service.factory.StageModelFactory;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.factory.VersionModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionOperationTestInterface;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertVersionOperationTest extends Assertions {

    @Inject
    UpsertTenantOperationTestInterface upsertTenantOperation;

    @Inject
    UpsertProjectOperationTestInterface upsertProjectOperation;

    @Inject
    UpsertStageOperationTestInterface upsertStageOperation;

    @Inject
    UpsertVersionOperationTestInterface upsertVersionOperation;

    @Inject
    TenantModelFactory tenantModelFactory;

    @Inject
    ProjectModelFactory projectModelFactory;

    @Inject
    StageModelFactory stageModelFactory;

    @Inject
    VersionModelFactory versionModelFactory;

    @Inject
    GenerateIdOperation generateIdOperation;

    @Test
    void givenVersion_whenUpsertVersion_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var version = versionModelFactory.create(tenant.getId(), stage.getId(), VersionConfigModel.create(),
                VersionSourceCodeModel.create());
        final var changeContext = upsertVersionOperation.upsertVersion(shard, version);
        assertTrue(changeContext.getResult());
    }

    @Test
    void givenVersion_whenUpsertVersion_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var version = versionModelFactory.create(tenant.getId(), stage.getId(), VersionConfigModel.create(),
                VersionSourceCodeModel.create());
        upsertVersionOperation.upsertVersion(shard, version);

        final var changeContext = upsertVersionOperation.upsertVersion(shard, version);
        assertFalse(changeContext.getResult());
    }

    @Test
    void givenVersion_whenUpsertVersion_thenIdempotencyViolation() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var version1 = versionModelFactory.create(tenant.getId(),
                stage.getId(),
                VersionConfigModel.create(),
                VersionSourceCodeModel.create());
        upsertVersionOperation.upsertVersion(shard, version1);

        final var version2 = versionModelFactory.create(tenant.getId(),
                stage.getId(),
                VersionConfigModel.create(),
                VersionSourceCodeModel.create(),
                version1.getIdempotencyKey());
        final var exception = assertThrows(ServerSideConflictException.class, () ->
                upsertVersionOperation.upsertVersion(shard, version2));
        assertEquals(ExceptionQualifierEnum.IDEMPOTENCY_VIOLATION, exception.getQualifier());
    }
}
