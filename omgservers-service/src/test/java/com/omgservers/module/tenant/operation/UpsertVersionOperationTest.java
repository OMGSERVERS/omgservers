package com.omgservers.module.tenant.operation;

import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.stage.StageConfigModel;
import com.omgservers.model.tenant.TenantConfigModel;
import com.omgservers.model.version.VersionBytecodeModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.module.tenant.factory.ProjectModelFactory;
import com.omgservers.module.tenant.factory.StageModelFactory;
import com.omgservers.module.tenant.factory.TenantModelFactory;
import com.omgservers.module.tenant.factory.VersionModelFactory;
import com.omgservers.module.tenant.impl.operation.upsertProject.UpsertProjectOperation;
import com.omgservers.module.tenant.impl.operation.upsertStage.UpsertStageOperation;
import com.omgservers.module.tenant.impl.operation.upsertTenant.UpsertTenantOperation;
import com.omgservers.module.tenant.impl.operation.upsertVersion.UpsertVersionOperation;
import com.omgservers.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class UpsertVersionOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    UpsertTenantOperation upsertTenantOperation;

    @Inject
    UpsertProjectOperation upsertProjectOperation;

    @Inject
    UpsertStageOperation upsertStageOperation;

    @Inject
    UpsertVersionOperation upsertVersionOperation;

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

    @Inject
    PgPool pgPool;

    @Test
    void givenVersion_whenUpsertVersion_thenInserted() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId(), StageConfigModel.create());
        upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, tenant.getId(), stage);
        final var version = versionModelFactory.create(tenant.getId(), stage.getId(), VersionConfigModel.create(), VersionSourceCodeModel.create(), VersionBytecodeModel.create());
        assertTrue(upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, tenant.getId(), version));
    }

    @Test
    void givenVersion_whenUpsertVersion_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create(TenantConfigModel.create());
        upsertTenantOperation.upsertTenant(TIMEOUT, pgPool, shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId(), StageConfigModel.create());
        upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, tenant.getId(), stage);
        final var version = versionModelFactory.create(tenant.getId(), stage.getId(), VersionConfigModel.create(), VersionSourceCodeModel.create(), VersionBytecodeModel.create());
        upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, tenant.getId(), version);

        assertFalse(upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, tenant.getId(), version));
    }
}
