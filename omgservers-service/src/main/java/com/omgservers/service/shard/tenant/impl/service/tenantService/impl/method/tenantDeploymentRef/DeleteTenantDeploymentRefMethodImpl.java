package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantDeploymentRef;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.tenant.tenantDeploymentRef.DeleteTenantDeploymentRefRequest;
import com.omgservers.schema.module.tenant.tenantDeploymentRef.DeleteTenantDeploymentRefResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantDeploymentRef.DeleteTenantDeploymentRefOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DeleteTenantDeploymentRefMethodImpl implements DeleteTenantDeploymentRefMethod {

    final DeleteTenantDeploymentRefOperation deleteTenantDeploymentRefOperation;
    final ChangeWithContextOperation changeWithContextOperation;

    @Override
    public Uni<DeleteTenantDeploymentRefResponse> execute(final ShardModel shardModel,
                                                          final DeleteTenantDeploymentRefRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return changeWithContextOperation.<Boolean>changeWithContext(
                        (changeContext, sqlConnection) ->
                                deleteTenantDeploymentRefOperation.execute(changeContext,
                                        sqlConnection,
                                        shardModel.shard(),
                                        tenantId,
                                        id))
                .map(ChangeContext::getResult)
                .map(DeleteTenantDeploymentRefResponse::new);
    }
}
