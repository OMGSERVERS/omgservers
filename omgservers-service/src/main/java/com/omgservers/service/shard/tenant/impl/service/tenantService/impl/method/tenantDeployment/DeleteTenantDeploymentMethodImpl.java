package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeployment;

import com.omgservers.schema.module.tenant.tenantDeployment.DeleteTenantDeploymentRequest;
import com.omgservers.schema.module.tenant.tenantDeployment.DeleteTenantDeploymentResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeployment.DeleteTenantDeploymentOperation;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantDeploymentMethodImpl implements DeleteTenantDeploymentMethod {

    final DeleteTenantDeploymentOperation deleteTenantDeploymentOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteTenantDeploymentResponse> execute(final DeleteTenantDeploymentRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteTenantDeploymentOperation.execute(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                tenantId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteTenantDeploymentResponse::new);
    }
}
