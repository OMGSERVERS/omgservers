package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRequest.viewVersionLobbyRequests;

import com.omgservers.schema.module.tenant.ViewVersionLobbyRequestsRequest;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRequestsResponse;
import com.omgservers.service.module.tenant.impl.operation.versionLobbyRequest.selectActiveVersionLobbyRequestsByVersionId.SelectActiveVersionLobbyRequestsByVersionId;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewVersionLobbyRequestsMethodImpl implements ViewVersionLobbyRequestsMethod {

    final SelectActiveVersionLobbyRequestsByVersionId selectActiveVersionLobbyRequestsByVersionId;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewVersionLobbyRequestsResponse> viewVersionLobbyRequests(
            final ViewVersionLobbyRequestsRequest request) {
        log.debug("View version lobby requests, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveVersionLobbyRequestsByVersionId
                            .selectActiveVersionLobbyRequestsByVersionId(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    versionId
                            )
                    );
                })
                .map(ViewVersionLobbyRequestsResponse::new);

    }
}
