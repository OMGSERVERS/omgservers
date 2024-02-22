package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.getVersionLobbyRequest;

import com.omgservers.model.dto.tenant.GetVersionLobbyRequestRequest;
import com.omgservers.model.dto.tenant.GetVersionLobbyRequestResponse;
import com.omgservers.service.module.tenant.impl.operation.selectVersionLobbyRequest.SelectVersionLobbyRequestOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetVersionLobbyRequestMethodImpl implements GetVersionLobbyRequestMethod {

    final SelectVersionLobbyRequestOperation selectVersionLobbyRequestOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetVersionLobbyRequestResponse> getVersionLobbyRequest(final GetVersionLobbyRequestRequest request) {
        log.debug("Get version lobby request, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var tenantId = request.getTenantId();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionLobbyRequestOperation
                            .selectVersionLobbyRequest(sqlConnection, shard, tenantId, id));
                })
                .map(GetVersionLobbyRequestResponse::new);
    }
}
