package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantMatchmakerResource;

import com.omgservers.schema.module.tenant.tenantMatchmakerResource.DeleteTenantMatchmakerResourceRequest;
import com.omgservers.schema.module.tenant.tenantMatchmakerResource.DeleteTenantMatchmakerResourceResponse;
import com.omgservers.service.shard.tenant.impl.operation.tenantMatchmakerResource.DeleteTenantMatchmakerResourceOperation;
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
class DeleteTenantMatchmakerResourceMethodImpl implements DeleteTenantMatchmakerResourceMethod {

    final DeleteTenantMatchmakerResourceOperation deleteTenantMatchmakerResourceOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteTenantMatchmakerResourceResponse> execute(
            final DeleteTenantMatchmakerResourceRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var id = request.getId();

        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeWithContextOperation.<Boolean>changeWithContext(
                                (changeContext, sqlConnection) ->
                                        deleteTenantMatchmakerResourceOperation.execute(changeContext,
                                                sqlConnection,
                                                shardModel.shard(),
                                                tenantId,
                                                id))
                        .map(ChangeContext::getResult))
                .map(DeleteTenantMatchmakerResourceResponse::new);
    }
}
