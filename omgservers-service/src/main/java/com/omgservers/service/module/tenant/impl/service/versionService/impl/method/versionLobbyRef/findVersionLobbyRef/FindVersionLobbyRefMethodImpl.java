package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.findVersionLobbyRef;

import com.omgservers.schema.module.tenant.FindVersionLobbyRefRequest;
import com.omgservers.schema.module.tenant.FindVersionLobbyRefResponse;
import com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.selectVersionLobbyRefByLobbyId.SelectVersionLobbyRefByLobbyIdOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindVersionLobbyRefMethodImpl implements FindVersionLobbyRefMethod {

    final SelectVersionLobbyRefByLobbyIdOperation selectVersionLobbyRefByLobbyIdOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<FindVersionLobbyRefResponse> findVersionLobbyRef(final FindVersionLobbyRefRequest request) {
        log.debug("Find version lobby ref, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    final var lobbyId = request.getLobbyId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionLobbyRefByLobbyIdOperation
                            .selectVersionLobbyRefByLobbyId(sqlConnection,
                                    shardModel.shard(),
                                    tenantId,
                                    versionId,
                                    lobbyId));
                })
                .map(FindVersionLobbyRefResponse::new);
    }
}
