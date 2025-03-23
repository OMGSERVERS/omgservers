package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyResource;

import com.omgservers.schema.module.tenant.tenantLobbyResource.UpdateTenantLobbyResourceStatusRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.UpdateTenantLobbyResourceStatusResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantLobbyResource.UpdateTenantLobbyResourceStatusOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class UpdateTenantLobbyResourceStatusMethodImpl implements UpdateTenantLobbyResourceStatusMethod {

    final UpdateTenantLobbyResourceStatusOperation updateTenantLobbyResourceStatusOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdateTenantLobbyResourceStatusResponse> execute(final UpdateTenantLobbyResourceStatusRequest request) {
        log.trace("{}", request);

        final var tenantId = request.getTenantId();
        final var tenantLobbyResourceId = request.getId();
        final var status = request.getStatus();
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                            updateTenantLobbyResourceStatusOperation.execute(changeContext,
                                    sqlConnection,
                                    shard,
                                    tenantId,
                                    tenantLobbyResourceId,
                                    status
                            ));
                })
                .map(ChangeContext::getResult)
                .map(UpdateTenantLobbyResourceStatusResponse::new);
    }
}
