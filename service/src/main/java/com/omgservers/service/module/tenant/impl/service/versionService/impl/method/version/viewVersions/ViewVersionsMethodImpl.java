package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.version.viewVersions;

import com.omgservers.model.dto.tenant.ViewVersionsRequest;
import com.omgservers.model.dto.tenant.ViewVersionsResponse;
import com.omgservers.service.module.tenant.impl.operation.version.selectActiveVersionsByStageId.SelectActiveVersionsByStageIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewVersionsMethodImpl implements ViewVersionsMethod {

    final SelectActiveVersionsByStageIdOperation selectActiveVersionsByStageIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewVersionsResponse> viewVersions(final ViewVersionsRequest request) {
        log.debug("View versions, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var stageId = request.getStageId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveVersionsByStageIdOperation
                            .selectActiveVersionsByStageId(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    stageId
                            )
                    );
                })
                .map(ViewVersionsResponse::new);
    }
}
