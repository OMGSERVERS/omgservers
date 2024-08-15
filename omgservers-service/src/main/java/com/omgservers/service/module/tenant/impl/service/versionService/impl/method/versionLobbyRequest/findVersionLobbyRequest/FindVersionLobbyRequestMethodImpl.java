package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.findVersionLobbyRequest;

import com.omgservers.schema.module.tenant.FindVersionLobbyRequestRequest;
import com.omgservers.schema.module.tenant.FindVersionLobbyRequestResponse;
import com.omgservers.service.module.tenant.impl.operation.versionLobbyRequest.selectVersionLobbyRequestByLobbyId.SelectVersionLobbyRequestByLobbyIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindVersionLobbyRequestMethodImpl implements FindVersionLobbyRequestMethod {

    final SelectVersionLobbyRequestByLobbyIdOperation selectVersionLobbyRequestByLobbyIdOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindVersionLobbyRequestResponse> findVersionLobbyRequest(final FindVersionLobbyRequestRequest request) {
        log.debug("Find version lobby request, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    final var lobbyId = request.getLobbyId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionLobbyRequestByLobbyIdOperation
                            .selectVersionLobbyRequestByLobbyId(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    versionId,
                                    lobbyId));
                })
                .map(FindVersionLobbyRequestResponse::new);
    }
}
