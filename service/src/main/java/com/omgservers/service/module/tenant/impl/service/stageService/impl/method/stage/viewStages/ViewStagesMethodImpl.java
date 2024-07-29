package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.stage.viewStages;

import com.omgservers.schema.module.tenant.ViewStagesRequest;
import com.omgservers.schema.module.tenant.ViewStagesResponse;
import com.omgservers.service.module.tenant.impl.operation.stage.selectActiveStagesByProjectId.SelectActiveStagesByProjectIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewStagesMethodImpl implements ViewStagesMethod {

    final SelectActiveStagesByProjectIdOperation selectActiveStagesByProjectIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewStagesResponse> viewStages(final ViewStagesRequest request) {
        log.debug("View stages, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var projectId = request.getProjectId();
                    return pgPool.withTransaction(sqlConnection -> selectActiveStagesByProjectIdOperation
                            .selectActiveStagesByProjectId(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    projectId
                            )
                    );
                })
                .map(ViewStagesResponse::new);

    }
}
