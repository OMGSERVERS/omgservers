package com.omgservers.service.module.tenant.operation.testOperation;

import com.omgservers.model.version.VersionConfigModel;
import com.omgservers.model.version.VersionSourceCodeModel;
import com.omgservers.service.factory.ProjectModelFactory;
import com.omgservers.service.factory.StageModelFactory;
import com.omgservers.service.factory.TenantModelFactory;
import com.omgservers.service.factory.VersionModelFactory;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertProjectOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertStageOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertTenantOperationTestInterface;
import com.omgservers.service.module.tenant.operation.testInterface.UpsertVersionOperationTestInterface;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
class CreateTestVersionOperationImpl implements CreateTestVersionOperation {

    final UpsertProjectOperationTestInterface upsertProjectOperation;
    final UpsertVersionOperationTestInterface upsertVersionOperation;
    final UpsertTenantOperationTestInterface upsertTenantOperation;
    final UpsertStageOperationTestInterface upsertStageOperation;

    final ProjectModelFactory projectModelFactory;
    final VersionModelFactory versionModelFactory;
    final TenantModelFactory tenantModelFactory;
    final StageModelFactory stageModelFactory;

    @Override
    public TestVersionHolder createTestVersion() {
        int shard = 0;
        final var tenant = tenantModelFactory.create();
        upsertTenantOperation.upsertTenant(shard, tenant);
        final var project = projectModelFactory.create(tenant.getId());
        upsertProjectOperation.upsertProject(shard, project);
        final var stage = stageModelFactory.create(tenant.getId(), project.getId());
        upsertStageOperation.upsertStage(shard, stage);
        final var version = versionModelFactory.create(tenant.getId(), stage.getId(), VersionConfigModel.create(),
                VersionSourceCodeModel.create());
        upsertVersionOperation.upsertVersion(shard, version);

        return new TestVersionHolder(tenant,
                project,
                stage,
                version);
    }
}
