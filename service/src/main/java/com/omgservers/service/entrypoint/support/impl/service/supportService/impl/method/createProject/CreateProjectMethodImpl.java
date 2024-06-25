package com.omgservers.service.entrypoint.support.impl.service.supportService.impl.method.createProject;

import com.omgservers.model.dto.support.CreateProjectSupportRequest;
import com.omgservers.model.dto.support.CreateProjectSupportResponse;
import com.omgservers.model.dto.tenant.SyncProjectRequest;
import com.omgservers.model.dto.tenant.SyncStageRequest;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.service.factory.tenant.ProjectModelFactory;
import com.omgservers.service.factory.tenant.StageModelFactory;
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

    final ProjectModelFactory projectModelFactory;
    final StageModelFactory stageModelFactory;

    @Override
    public Uni<CreateProjectSupportResponse> createProject(final CreateProjectSupportRequest request) {
        log.debug("Create project, request={}", request);

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

    Uni<ProjectModel> createProject(final Long tenantId) {
        final var project = projectModelFactory.create(tenantId);
        final var syncProjectInternalRequest = new SyncProjectRequest(project);
        return tenantModule.getProjectService().syncProject(syncProjectInternalRequest)
                .replaceWith(project);
    }

    Uni<StageModel> createStage(final Long tenantId,
                                final Long projectId) {
        final var stage = stageModelFactory.create(tenantId, projectId);
        final var syncStageInternalRequest = new SyncStageRequest(stage);
        return tenantModule.getStageService().syncStage(syncStageInternalRequest)
                .replaceWith(stage);
    }
}
