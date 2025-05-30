package com.omgservers.service.server.task.impl.method.executeStageTask;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesResponse;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.server.task.Task;
import com.omgservers.service.server.task.TaskResult;
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
public class StageTaskImpl implements Task<StageTaskArguments> {

    final TenantShard tenantShard;

    public Uni<TaskResult> execute(final StageTaskArguments taskArguments) {
        final var tenantId = taskArguments.tenantId();
        final var tenantStageId = taskArguments.tenantStageId();

        return getTenantStage(tenantId, tenantStageId)
                .flatMap(tenantStage -> handleTenantStage(tenantStage)
                        .replaceWith(TaskResult.DONE));
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Void> handleTenantStage(final TenantStageModel tenantStage) {
        final var tenantId = tenantStage.getTenantId();
        final var tenantStageId = tenantStage.getId();

        return viewTenantDeploymentResources(tenantId, tenantStageId)
                .flatMap(tenantDeploymentResources -> {
                    if (tenantDeploymentResources.size() > 1) {
                        final var previouslyCreatedDeployments = tenantDeploymentResources
                                .subList(0, tenantDeploymentResources.size() - 1);
                        return Multi.createFrom().iterable(previouslyCreatedDeployments)
                                .onItem().transformToUniAndConcatenate(this::closePreviouslyCreatedDeployment)
                                .collect().asList()
                                .replaceWithVoid();
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<List<TenantDeploymentResourceModel>> viewTenantDeploymentResources(final Long tenantId, final Long tenantStageId) {
        final var request = new ViewTenantDeploymentResourcesRequest();
        request.setTenantId(tenantId);
        request.setTenantStageId(tenantStageId);
        request.setStatus(TenantDeploymentResourceStatusEnum.CREATED);
        return tenantShard.getService().execute(request)
                .map(ViewTenantDeploymentResourcesResponse::getTenantDeploymentResources);
    }

    Uni<Boolean> closePreviouslyCreatedDeployment(final TenantDeploymentResourceModel tenantDeploymentResource) {
        final var tenantId = tenantDeploymentResource.getTenantId();
        final var tenantDeploymentResourceId = tenantDeploymentResource.getId();
        final var request = new UpdateTenantDeploymentResourceStatusRequest(tenantId,
                tenantDeploymentResourceId,
                TenantDeploymentResourceStatusEnum.CLOSED);
        return tenantShard.getService().execute(request)
                .map(UpdateTenantDeploymentResourceStatusResponse::getUpdated);
    }
}
