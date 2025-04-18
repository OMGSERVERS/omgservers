package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.DeleteTenantDeploymentResourceRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.DeleteTenantDeploymentResourceResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource.DeleteTenantDeploymentResourceOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantDeploymentResourceMethodImpl implements DeleteTenantDeploymentResourceMethod {

    final DeleteTenantDeploymentResourceOperation deleteTenantDeploymentResourceOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteTenantDeploymentResourceResponse> execute(final ShardModel shardModel,
                                                               final DeleteTenantDeploymentResourceRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) ->
                                deleteTenantDeploymentResourceOperation.execute(changeContext,
                                        sqlConnection,
                                        shardModel.slot(),
                                        tenantId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteTenantDeploymentResourceResponse::new);
    }
}
