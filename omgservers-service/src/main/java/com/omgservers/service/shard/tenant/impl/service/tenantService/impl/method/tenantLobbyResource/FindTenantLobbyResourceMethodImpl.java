package com.omgservers.service.shard.tenant.impl.service.tenantService.impl.method.tenantLobbyResource;

import com.omgservers.schema.module.tenant.tenantLobbyResource.FindTenantLobbyResourceRequest;
import com.omgservers.schema.module.tenant.tenantLobbyResource.FindTenantLobbyResourceResponse;
import com.omgservers.service.operation.server.CheckShardOperation;
import com.omgservers.service.shard.tenant.impl.operation.tenantLobbyResource.SelectTenantLobbyResourceByLobbyIdOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindTenantLobbyResourceMethodImpl implements FindTenantLobbyResourceMethod {

    final SelectTenantLobbyResourceByLobbyIdOperation selectTenantLobbyResourceByLobbyIdOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindTenantLobbyResourceResponse> execute(final FindTenantLobbyResourceRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var tenantDeploymentId = request.getTenantDeploymentId();
                    final var lobbyId = request.getLobbyId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantLobbyResourceByLobbyIdOperation
                            .execute(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    tenantDeploymentId,
                                    lobbyId));
                })
                .map(FindTenantLobbyResourceResponse::new);
    }
}
