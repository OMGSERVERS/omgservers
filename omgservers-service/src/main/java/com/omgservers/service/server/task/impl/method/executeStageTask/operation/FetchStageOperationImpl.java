package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantStage.TenantStageModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesResponse;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageRequest;
import com.omgservers.schema.shard.tenant.tenantStage.GetTenantStageResponse;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchStageResult;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class FetchStageOperationImpl implements FetchStageOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<FetchStageResult> execute(final Long tenantId, final Long tenantStageId) {
        return getTenantStage(tenantId, tenantStageId)
                .flatMap(tenantStage -> viewTenantDeploymentResources(tenantId, tenantStageId)
                        .map(tenantDeploymentResources ->
                                new FetchStageResult(tenantStage, tenantDeploymentResources)));
    }

    Uni<TenantStageModel> getTenantStage(final Long tenantId, final Long id) {
        final var request = new GetTenantStageRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(GetTenantStageResponse::getTenantStage);
    }

    Uni<List<TenantDeploymentResourceModel>> viewTenantDeploymentResources(final Long tenantId,
                                                                           final Long tenantStageId) {
        final var request = new ViewTenantDeploymentResourcesRequest();
        request.setTenantId(tenantId);
        request.setTenantStageId(tenantStageId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantDeploymentResourcesResponse::getTenantDeploymentResources);
    }
}
