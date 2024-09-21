package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createProject;

import com.omgservers.schema.entrypoint.support.CreateProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateProjectSupportResponse;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.service.factory.tenant.TenantProjectModelFactory;
import com.omgservers.service.factory.tenant.TenantStageModelFactory;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateProjectMethodImpl implements CreateProjectMethod {

    final TenantModule tenantModule;

    final TenantProjectModelFactory tenantProjectModelFactory;
    final TenantStageModelFactory tenantStageModelFactory;

    @Override
    public Uni<CreateProjectSupportResponse> createProject(final CreateProjectSupportRequest request) {
        log.debug("Create tenant project, request={}", request);

        final var tenantId = request.getTenantId();
        return createProject(tenantId)
                .flatMap(project -> {
                    final var projectId = project.getId();
                    return createStage(tenantId, projectId)
                            .map(stage -> {
                                final var stageId = stage.getId();
                                final var stageSecret = stage.getSecret();
                                return new CreateProjectSupportResponse(projectId, stageId, stageSecret);
                            });
                });
    }

    Uni<TenantProjectModel> createProject(final Long tenantId) {
        final var project = tenantProjectModelFactory.create(tenantId);
        final var syncProjectInternalRequest = new SyncTenantProjectRequest(project);
        return tenantModule.getTenantService().syncProject(syncProjectInternalRequest)
                .replaceWith(project);
    }

    Uni<TenantStageModel> createStage(final Long tenantId,
                                      final Long projectId) {
        final var stage = tenantStageModelFactory.create(tenantId, projectId);
        final var syncStageInternalRequest = new SyncTenantStageRequest(stage);
        return tenantModule.getTenantService().syncTenantStage(syncStageInternalRequest)
                .replaceWith(stage);
    }
}
