package com.omgservers.service.module.tenant.impl.service.tenantService.impl.method.tenantLobbyRef;

import com.omgservers.schema.module.tenant.tenantLobbyRef.FindTenantLobbyRefRequest;
import com.omgservers.schema.module.tenant.tenantLobbyRef.FindTenantLobbyRefResponse;
import com.omgservers.service.module.tenant.impl.operation.tenantLobbyRef.SelectTenantLobbyRefByLobbyIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindTenantLobbyRefMethodImpl implements FindTenantLobbyRefMethod {

    final SelectTenantLobbyRefByLobbyIdOperation selectTenantLobbyRefByLobbyIdOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindTenantLobbyRefResponse> execute(final FindTenantLobbyRefRequest request) {
        log.debug("Find tenant lobby ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var tenantDeploymentId = request.getDeploymentId();
                    final var lobbyId = request.getLobbyId();
                    return pgPool.withTransaction(sqlConnection -> selectTenantLobbyRefByLobbyIdOperation
                            .execute(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    tenantDeploymentId,
                                    lobbyId));
                })
                .map(FindTenantLobbyRefResponse::new);
    }
}
