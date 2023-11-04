package com.omgservers.module.tenant.impl.service.versionService.impl.method.viewVersionMatchmakers;

import com.omgservers.model.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersResponse;
import com.omgservers.module.tenant.impl.operation.selectVersionMatchmakers.SelectVersionMatchmakers;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewVersionMatchmakersMethodImpl implements ViewVersionMatchmakersMethod {

    final SelectVersionMatchmakers selectVersionMatchmakers;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewVersionMatchmakersResponse> viewVersionMatchmakers(final ViewVersionMatchmakersRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var versionId = request.getVersionId();
                    final var deleted = request.getDeleted();
                    return pgPool.withTransaction(sqlConnection -> selectVersionMatchmakers
                            .selectVersionMatchmakers(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    versionId,
                                    deleted));
                })
                .map(ViewVersionMatchmakersResponse::new);

    }
}
