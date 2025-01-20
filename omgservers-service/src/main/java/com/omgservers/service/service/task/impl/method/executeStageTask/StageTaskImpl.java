package com.omgservers.service.service.task.impl.method.executeStageTask;

import com.omgservers.schema.model.tenantDeployment.TenantDeploymentModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.module.tenant.tenantDeployment.DeleteTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.DeleteTenantDeploymentResponse;
import com.omgservers.schema.module.tenant.tenantDeployment.ViewTenantDeploymentsRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.ViewTenantDeploymentsResponse;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.module.tenant.tenantStage.GetTenantStageResponse;
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
public class StageTaskImpl {

    final RuntimeShard runtimeShard;
    final TenantShard tenantShard;

    public Uni<Boolean> execute(final Long tenantId, final Long tenantStageId) {
        return getTenantStage(tenantId, tenantStageId)
                .flatMap(tenantStage -> handleTenantStage(tenantStage)
                        .replaceWith(Boolean.TRUE));
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantShard.getService().getTenantStage(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<Void> handleTenantStage(final TenantStageModel tenantStage) {
        final var tenantId = tenantStage.getTenantId();
        final var tenantStageId = tenantStage.getId();

        return viewTenantDeployments(tenantId, tenantStageId)
                .flatMap(tenantDeployments -> {
                    if (tenantDeployments.size() > 1) {
                        final var previouslyCreatedDeployments = tenantDeployments
                                .subList(0, tenantDeployments.size() - 1);
                        // TODO: delete only deployments without clients
                        return Multi.createFrom().iterable(previouslyCreatedDeployments)
                                .onItem().transformToUniAndConcatenate(this::deletePreviouslyCreatedDeployment)
                                .collect().asList()
                                .invoke(results -> {
                                    final var deleted = results.stream().filter(Boolean.TRUE::equals).count();
                                    if (deleted > 0) {
                                        log.info("The \"{}\" previously created deployments " +
                                                        "in stage \"{}\" of tenant \"{}\" were deleted",
                                                deleted, tenantStageId, tenantId);
                                    }
                                })
                                .replaceWithVoid();
                    } else {
                        return Uni.createFrom().voidItem();
                    }
                });
    }

    Uni<List<TenantDeploymentModel>> viewTenantDeployments(final Long tenantId, final Long tenantStageId) {
        final var request = new ViewTenantDeploymentsRequest(tenantId, tenantStageId);
        return tenantShard.getService().viewTenantDeployments(request)
                .map(ViewTenantDeploymentsResponse::getTenantDeployments);
    }

    Uni<Boolean> deletePreviouslyCreatedDeployment(final TenantDeploymentModel tenantDeployment) {
        final var tenantId = tenantDeployment.getTenantId();
        final var tenantDeploymentId = tenantDeployment.getId();
        final var request = new DeleteTenantDeploymentRequest(tenantId, tenantDeploymentId);
        return tenantShard.getService().deleteTenantDeployment(request)
                .map(DeleteTenantDeploymentResponse::getDeleted);
    }
}
