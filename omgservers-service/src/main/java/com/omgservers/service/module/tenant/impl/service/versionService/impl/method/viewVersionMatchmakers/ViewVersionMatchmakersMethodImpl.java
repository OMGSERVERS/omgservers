package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersionMatchmakers;

import com.omgservers.model.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersResponse;
import com.omgservers.service.module.tenant.impl.operation.selectActiveVersionMatchmakers.SelectActiveVersionMatchmakers;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewVersionMatchmakersMethodImpl implements ViewVersionMatchmakersMethod {

    final SelectActiveVersionMatchmakers selectActiveVersionMatchmakers;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewVersionMatchmakersResponse> viewVersionMatchmakers(final ViewVersionMatchmakersRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveVersionMatchmakers
                            .selectActiveVersionMatchmakers(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    versionId));
                })
                .map(ViewVersionMatchmakersResponse::new);

    }
}
