package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentResource;

import com.omgservers.schema.module.tenant.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentResource.UpdateTenantDeploymentResourceStatusResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
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
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdateTenantDeploymentResourceStatusResponse> execute(final UpdateTenantDeploymentResourceStatusRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();
        final var status = request.getStatus();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                            updateTenantDeploymentResourceStatusOperation.execute(changeContext,
                                    sqlConnection,
                                    shard,
                                    tenantId,
                                    id,
                                    status
                            ));
                })
                .map(ChangeContext::getResult)
                .map(UpdateTenantDeploymentResourceStatusResponse::new);
    }
}
