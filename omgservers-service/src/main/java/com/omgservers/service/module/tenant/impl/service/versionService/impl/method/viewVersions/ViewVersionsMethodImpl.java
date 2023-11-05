package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersions;

import com.omgservers.model.dto.tenant.ViewVersionsRequest;
import com.omgservers.model.dto.tenant.ViewVersionsResponse;
import com.omgservers.service.module.tenant.impl.operation.selectVersionsByStageId.SelectVersionsByStageIdOperation;
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

    final SelectVersionsByStageIdOperation selectVersionsByStageIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewVersionsResponse> viewVersions(final ViewVersionsRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var stageId = request.getStageId();
                    final var deleted = request.getDeleted();
                    return pgPool.withTransaction(sqlConnection -> selectVersionsByStageIdOperation
                            .selectVersionsByStageId(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    stageId,
                                    deleted));
                })
                .map(ViewVersionsResponse::new);
    }
}
