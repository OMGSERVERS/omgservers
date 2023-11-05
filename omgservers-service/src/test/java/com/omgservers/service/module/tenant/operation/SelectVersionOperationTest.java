package com.omgservers.service.module.tenant.operation;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.service.exception.ServerSideNotFoundException;
import com.omgservers.service.factory.ProjectModelFactory;
import com.omgservers.service.factory.StageModelFactory;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.factory.VersionModelFactory;
import com.omgservers.service.operation.generateId.GenerateIdOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
@QuarkusTest
class SelectVersionOperationTest extends Assertions {
    private static final long TIMEOUT = 1L;

    @Inject
    SelectVersionOperationTestInterface selectVersionOperation;

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

    @Inject
    PgPool pgPool;

    @Test
    void givenVersion_whenSelectVersion_thenSelected() {
        final var shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var version1 = versionModelFactory.create(tenant.getId(), stage.getId(), VersionConfigModel.create(),
                VersionSourceCodeModel.create());
        upsertVersionOperation.upsertVersion(shard, version1);

        final var version2 = selectVersionOperation.selectVersion(shard, tenant.getId(), version1.getId(), false);
        assertEquals(version1, version2);
    }

    @Test
    void givenUnknownIds_whenSelectVersion_thenException() {
        final var shard = 0;
        final var id = generateIdOperation.generateId();

        assertThrows(ServerSideNotFoundException.class, () -> selectVersionOperation
                .selectVersion(shard, tenantId(), id, false));
    }

    Long tenantId() {
        return generateIdOperation.generateId();
    }
}