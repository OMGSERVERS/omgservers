package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.viewVersions;

import com.omgservers.schema.module.tenant.ViewVersionsRequest;
import com.omgservers.schema.module.tenant.ViewVersionsResponse;
import com.omgservers.service.module.tenant.impl.operation.version.selectActiveVersionProjectionsByStageId.SelectActiveVersionProjectionsByStageIdOperation;
import com.omgservers.service.server.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewVersionsMethodImpl implements ViewVersionsMethod {

    final SelectActiveVersionProjectionsByStageIdOperation selectActiveVersionProjectionsByStageIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewVersionsResponse> viewVersions(final ViewVersionsRequest request) {
        log.debug("View versions, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var stageId = request.getStageId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveVersionProjectionsByStageIdOperation
                            .selectActiveVersionProjectionsByStageId(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    stageId
                            )
                    );
                })
                .map(ViewVersionsResponse::new);
    }
}
