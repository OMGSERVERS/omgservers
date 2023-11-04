package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.project.ProjectConfigModel;
import com.omgservers.model.stage.StageConfigModel;
import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.service.factory.ProjectModelFactory;
import com.omgservers.service.factory.StageModelFactory;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.factory.VersionModelFactory;
import com.omgservers.service.module.tenant.impl.operation.upsertProject.UpsertProjectOperation;
import com.omgservers.service.module.tenant.impl.operation.upsertStage.UpsertStageOperation;
import com.omgservers.service.module.tenant.impl.operation.upsertVersion.UpsertVersionOperation;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
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
    UpsertTenantOperationTestInterface upsertTenantOperation;

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
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId(), StageConfigModel.create());
        upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, tenant.getId(), stage);
        final var version = versionModelFactory.create(tenant.getId(), stage.getId(), VersionConfigModel.create(),
                VersionSourceCodeModel.create());
        assertTrue(upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, tenant.getId(), version));
    }

    @Test
    void givenVersion_whenUpsertVersion_thenUpdated() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId(), ProjectConfigModel.create());
        upsertProjectOperation.upsertProject(TIMEOUT, pgPool, shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId(), StageConfigModel.create());
        upsertStageOperation.upsertStage(TIMEOUT, pgPool, shard, tenant.getId(), stage);
        final var version = versionModelFactory.create(tenant.getId(), stage.getId(), VersionConfigModel.create(),
                VersionSourceCodeModel.create());
        upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, tenant.getId(), version);

        assertFalse(upsertVersionOperation.upsertVersion(TIMEOUT, pgPool, shard, tenant.getId(), version));
    }
}
