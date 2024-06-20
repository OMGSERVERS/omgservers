package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeTenantTask;

import com.omgservers.model.dto.system.task.ExecuteStageTaskRequest;
import com.omgservers.model.dto.tenant.GetTenantRequest;
import com.omgservers.model.dto.tenant.GetTenantResponse;
import com.omgservers.model.dto.tenant.ViewProjectsRequest;
import com.omgservers.model.dto.tenant.ViewProjectsResponse;
import com.omgservers.model.dto.tenant.ViewStagesRequest;
import com.omgservers.model.dto.tenant.ViewStagesResponse;
import com.omgservers.model.project.ProjectModel;
import com.omgservers.model.stage.StageModel;
import com.omgservers.model.tenant.TenantModel;
import com.omgservers.service.module.runtime.RuntimeModule;
import com.omgservers.service.module.system.SystemModule;
import com.omgservers.service.module.tenant.TenantModule;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantTaskImpl {

    final RuntimeModule runtimeModule;
    final TenantModule tenantModule;
    final SystemModule systemModule;

    public Uni<Boolean> executeTask(final Long tenantId) {
        return getTenant(tenantId)
                .flatMap(tenant -> handleTenant(tenant)
                        .replaceWith(Boolean.TRUE));
    }

    Uni<TenantModel> getTenant(final Long id) {
        final var request = new GetTenantRequest(id);
        return tenantModule.getTenantService().getTenant(request)
                .map(GetTenantResponse::getTenant);
    }

    Uni<Void> handleTenant(final TenantModel tenant) {
        final var tenantId = tenant.getId();
        return viewProjects(tenantId)
                .flatMap(projects -> Multi.createFrom().iterable(projects)
                        .onItem().transformToUniAndConcatenate(this::handleProject)
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<ProjectModel>> viewProjects(final Long tenantId) {
        final var request = new ViewProjectsRequest(tenantId);
        return tenantModule.getProjectService().viewProjects(request)
                .map(ViewProjectsResponse::getProjects);
    }

    Uni<Void> handleProject(final ProjectModel project) {
        final var tenantId = project.getTenantId();
        final var projectId = project.getId();
        return viewStages(tenantId, projectId)
                .flatMap(stages -> Multi.createFrom().iterable(stages)
                        .onItem().transformToUniAndConcatenate(this::handleStage)
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<StageModel>> viewStages(final Long tenantId, final Long projectId) {
        final var request = new ViewStagesRequest(tenantId, projectId);
        return tenantModule.getStageService().viewStages(request)
                .map(ViewStagesResponse::getStages);
    }

    Uni<Void> handleStage(final StageModel stage) {
        final var tenantId = stage.getTenantId();
        final var stageId = stage.getId();
        final var request = new ExecuteStageTaskRequest(tenantId, stageId);
        return systemModule.getTaskService().executeStageTask(request)
                .replaceWithVoid();
    }
}
