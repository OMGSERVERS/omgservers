package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionLobbyRef.viewVersionLobbyRefs;

import com.omgservers.schema.module.tenant.ViewVersionLobbyRefsRequest;
import com.omgservers.schema.module.tenant.ViewVersionLobbyRefsResponse;
import com.omgservers.service.module.tenant.impl.operation.versionLobbyRef.selectActiveVersionLobbyRefsByVersionId.SelectActiveVersionLobbyRefsByVersionId;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewVersionLobbyRefsMethodImpl implements ViewVersionLobbyRefsMethod {

    final SelectActiveVersionLobbyRefsByVersionId selectActiveVersionLobbyRefsByVersionId;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewVersionLobbyRefsResponse> viewVersionLobbyRefs(final ViewVersionLobbyRefsRequest request) {
        log.debug("View version lobby refs, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveVersionLobbyRefsByVersionId
                            .selectActiveVersionLobbyRefsByVersionId(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    versionId
                            )
                    );
                })
                .map(ViewVersionLobbyRefsResponse::new);

    }
}
