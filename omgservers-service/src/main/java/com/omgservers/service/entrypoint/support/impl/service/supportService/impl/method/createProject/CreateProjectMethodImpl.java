package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createProject;

import com.omgservers.schema.entrypoint.support.CreateProjectSupportRequest;
import com.omgservers.schema.entrypoint.support.CreateProjectSupportResponse;
import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantProject.SyncTenantProjectRequest;
import com.omgservers.schema.module.tenant.tenantStage.SyncTenantStageRequest;
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
        return createTenantProject(tenantId)
                .flatMap(tenantProject -> {
                    final var tenantProjectId = tenantProject.getId();
                    return createTenantStage(tenantId, tenantProjectId)
                            .map(tenantStage -> {
                                final var stageId = tenantStage.getId();
                                final var stageSecret = tenantStage.getSecret();
                                return new CreateProjectSupportResponse(tenantProjectId, stageId, stageSecret);
                            });
                });
    }

    Uni<TenantProjectModel> createTenantProject(final Long tenantId) {
        final var project = tenantProjectModelFactory.create(tenantId);
        final var syncProjectInternalRequest = new SyncTenantProjectRequest(project);
        return tenantModule.getTenantService().syncTenantProject(syncProjectInternalRequest)
                .replaceWith(project);
    }

    Uni<TenantStageModel> createTenantStage(final Long tenantId,
                                            final Long tenantProjectId) {
        final var tenantStage = tenantStageModelFactory.create(tenantId, tenantProjectId);
        final var syncStageInternalRequest = new SyncTenantStageRequest(tenantStage);
        return tenantModule.getTenantService().syncTenantStage(syncStageInternalRequest)
                .replaceWith(tenantStage);
    }
}
