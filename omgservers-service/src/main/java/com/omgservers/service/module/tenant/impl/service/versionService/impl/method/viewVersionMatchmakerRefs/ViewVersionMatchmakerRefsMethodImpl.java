package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersionMatchmakerRefs;

import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRefsRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRefsResponse;
import com.omgservers.service.module.tenant.impl.operation.selectActiveVersionMatchmakerRefsByVersionId.SelectActiveVersionMatchmakerRefsByVersionId;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewVersionMatchmakerRefsMethodImpl implements ViewVersionMatchmakerRefsMethod {

    final SelectActiveVersionMatchmakerRefsByVersionId selectActiveVersionMatchmakerRefsByVersionId;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewVersionMatchmakerRefsResponse> viewVersionMatchmakerRefs(
            final ViewVersionMatchmakerRefsRequest request) {
        log.debug("View version matchmaker refs, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveVersionMatchmakerRefsByVersionId
                            .selectActiveVersionMatchmakerRefsByVersionId(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    versionId));
                })
                .map(ViewVersionMatchmakerRefsResponse::new);

    }
}
