package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceModel;
import com.omgservers.schema.model.tenantDeploymentResource.TenantDeploymentResourceStatusEnum;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusResponse;
import com.omgservers.service.exception.ServerSideClientException;
import com.omgservers.service.shard.tenant.TenantShard;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CloseTenantDeploymentResourcesOperationImpl implements CloseTenantDeploymentResourcesOperation {

    final TenantShard tenantShard;

    @Override
    public Uni<Void> execute(final List<TenantDeploymentResourceModel> tenantDeploymentResources) {
        return Multi.createFrom().iterable(tenantDeploymentResources)
                .onItem().transformToUniAndConcatenate(tenantDeploymentResource ->
                        closeTenantDeploymentResource(tenantDeploymentResource)
                                .onFailure(ServerSideClientException.class)
                                .recoverWithItem(t -> {
                                    log.warn("Failed to close, id={}/{}, {}:{}",
                                            tenantDeploymentResource.getTenantId(),
                                            tenantDeploymentResource.getId(),
                                            t.getClass().getSimpleName(),
                                            t.getMessage());
                                    return null;
                                })
                )
                .collect().asList()
                .replaceWithVoid();
    }

    Uni<Boolean> closeTenantDeploymentResource(final TenantDeploymentResourceModel tenantDeploymentResource) {
        final var tenantId = tenantDeploymentResource.getTenantId();
        final var tenantDeploymentResourceId = tenantDeploymentResource.getId();
        final var request = new UpdateTenantDeploymentResourceStatusRequest(tenantId,
                tenantDeploymentResourceId,
                TenantDeploymentResourceStatusEnum.CLOSED);
        return tenantShard.getService().execute(request)
                .map(UpdateTenantDeploymentResourceStatusResponse::getUpdated);
    }
}
