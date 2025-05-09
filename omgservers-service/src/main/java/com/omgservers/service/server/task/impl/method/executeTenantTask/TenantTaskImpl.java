package com.omgservers.service.server.task.impl.method.executeTenantTask;

import com.omgservers.schema.model.project.TenantProjectModel;
import com.omgservers.schema.model.tenant.TenantModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.shard.tenant.tenant.GetTenantRequest;
import com.omgservers.schema.shard.tenant.tenant.GetTenantResponse;
import com.omgservers.schema.shard.tenant.tenantProject.ViewTenantProjectsRequest;
import com.omgservers.schema.shard.tenant.tenantProject.ViewTenantProjectsResponse;
import com.omgservers.schema.shard.tenant.tenantStage.ViewTenantStagesRequest;
import com.omgservers.schema.shard.tenant.tenantStage.ViewTenantStagesResponse;
import com.omgservers.service.server.task.Task;
import com.omgservers.service.server.task.TaskService;
import com.omgservers.service.server.task.dto.ExecuteStageTaskRequest;
import com.omgservers.service.shard.runtime.RuntimeShard;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantTaskImpl implements Task<TenantTaskArguments> {

    final RuntimeShard runtimeShard;
    final TenantShard tenantShard;

    final TaskService taskService;

    public Uni<Boolean> execute(final TenantTaskArguments taskArguments) {
        final var tenantId = taskArguments.tenantId();

        return getTenant(tenantId)
                .flatMap(tenant -> handleTenant(tenant)
                        .replaceWith(Boolean.TRUE));
    }

    Uni<TenantModel> getTenant(final Long id) {
        final var request = new GetTenantRequest(id);
        return tenantShard.getService().execute(request)
                .map(GetTenantResponse::getTenant);
    }

    Uni<Void> handleTenant(final TenantModel tenant) {
        final var tenantId = tenant.getId();
        return viewTenantProjects(tenantId)
                .flatMap(projects -> Multi.createFrom().iterable(projects)
                        .onItem().transformToUniAndConcatenate(this::handleProject)
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantProjectModel>> viewTenantProjects(final Long tenantId) {
        final var request = new ViewTenantProjectsRequest(tenantId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantProjectsResponse::getTenantProjects);
    }

    Uni<Void> handleProject(final TenantProjectModel project) {
        final var tenantId = project.getTenantId();
        final var tenantProjectId = project.getId();
        return viewTenantStages(tenantId, tenantProjectId)
                .flatMap(tenantStages -> Multi.createFrom().iterable(tenantStages)
                        .onItem().transformToUniAndConcatenate(this::handleStage)
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantStageModel>> viewTenantStages(final Long tenantId, final Long tenantProjectId) {
        final var request = new ViewTenantStagesRequest(tenantId, tenantProjectId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantStagesResponse::getTenantStages);
    }

    Uni<Void> handleStage(final TenantStageModel tenantStage) {
        final var tenantId = tenantStage.getTenantId();
        final var tenantStageId = tenantStage.getId();
        final var request = new ExecuteStageTaskRequest(tenantId, tenantStageId);
        return taskService.execute(request)
                .replaceWithVoid();
    }
}
