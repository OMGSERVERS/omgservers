package com.omgservers.service.operation.tenant;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.DeleteTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.DeleteTenantDeploymentResourceResponse;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.ViewTenantDeploymentResourcesResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class DeleteTenantStageDeploymentResourcesOperationImpl implements DeleteTenantStageDeploymentResourcesOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<Void> execute(final Long tenantId,
                             final Long tenantStageId) {
        return viewTenantDeploymentResources(tenantId, tenantStageId)
                .flatMap(tenantDeploymentResources -> Multi.createFrom().iterable(tenantDeploymentResources)
                        .onItem().transformToUniAndConcatenate(tenantDeploymentResource -> {
                                    final var tenantDeploymentResourcesId = tenantDeploymentResource.getId();
                                    return deleteTenantDeploymentResource(tenantId, tenantDeploymentResourcesId)
                                            .onFailure(ServerSideClientException.class)
                                            .recoverWithItem(t -> {
                                                log.warn("Failed to delete, id={}/{}, {}:{}",
                                                        tenantId,
                                                        tenantDeploymentResourcesId,
                                                        t.getClass().getSimpleName(),
                                                        t.getMessage());
                                                return null;
                                            });
                                }
                        )
                        .collect().asList()
                        .replaceWithVoid()
                );
    }

    Uni<List<TenantDeploymentResourceModel>> viewTenantDeploymentResources(final Long tenantId,
                                                                           final Long tenantStageId) {
        final var request = new ViewTenantDeploymentResourcesRequest();
        request.setTenantId(tenantId);
        request.setTenantStageId(tenantStageId);
        return tenantShard.getService().execute(request)
                .map(ViewTenantDeploymentResourcesResponse::getTenantDeploymentResources);
    }

    Uni<Boolean> deleteTenantDeploymentResource(final Long tenantId, final Long id) {
        final var request = new DeleteTenantDeploymentResourceRequest(tenantId, id);
        return tenantShard.getService().execute(request)
                .map(DeleteTenantDeploymentResourceResponse::getDeleted);
    }
}
