package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusRequest;
import com.omgservers.schema.shard.tenant.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateTenantDeploymentResourceStatusMethodImpl implements UpdateTenantDeploymentResourceStatusMethod {

    final UpdateTenantDeploymentResourceStatusOperation updateTenantDeploymentResourceStatusOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<UpdateTenantDeploymentResourceStatusResponse> execute(final ShardModel shardModel,
                                                                     final UpdateTenantDeploymentResourceStatusRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();
        final var status = request.getStatus();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        updateTenantDeploymentResourceStatusOperation.execute(changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                tenantId,
                                id,
                                status
                        ))
                .map(ChangeContext::getResult)
                .map(UpdateTenantDeploymentResourceStatusResponse::new);
    }
}
