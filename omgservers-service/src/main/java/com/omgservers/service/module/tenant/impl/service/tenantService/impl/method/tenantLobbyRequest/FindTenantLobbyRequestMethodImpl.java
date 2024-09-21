package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRequest;

import com.omgservers.schema.module.tenant.tenantLobbyRequest.FindTenantLobbyRequestRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRequest.FindTenantLobbyRequestResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantLobbyRequest.SelectTenantLobbyRequestByLobbyIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindTenantLobbyRequestMethodImpl implements FindTenantLobbyRequestMethod {

    final SelectTenantLobbyRequestByLobbyIdOperation selectTenantLobbyRequestByLobbyIdOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindTenantLobbyRequestResponse> execute(final FindTenantLobbyRequestRequest request) {
        log.debug("Find tenant lobby request, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getDeploymentId();
                    final var lobbyId = request.getLobbyId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantLobbyRequestByLobbyIdOperation
                            .execute(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    versionId,
                                    lobbyId));
                })
                .map(FindTenantLobbyRequestResponse::new);
    }
}
