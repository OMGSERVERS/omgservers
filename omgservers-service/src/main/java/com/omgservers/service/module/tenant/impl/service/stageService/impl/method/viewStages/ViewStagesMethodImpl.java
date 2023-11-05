package com.omgservers.service.module.tenant.impl.service.stageService.impl.method.viewStages;

import com.omgservers.model.dto.tenant.ViewStagesRequest;
import com.omgservers.model.dto.tenant.ViewStagesResponse;
import com.omgservers.service.module.tenant.impl.operation.selectStagesByProjectId.SelectStagesByProjectIdOperation;
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

    final SelectStagesByProjectIdOperation selectStagesByProjectIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewStagesResponse> viewStages(final ViewStagesRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var tenantId = request.getTenantId();
                    final var projectId = request.getProjectId();
                    final var deleted = request.getDeleted();
                    return pgPool.withTransaction(sqlConnection -> selectStagesByProjectIdOperation
                            .selectStagesByProjectId(sqlConnection,
                                    shard.shard(),
                                    tenantId,
                                    projectId,
                                    deleted));
                })
                .map(ViewStagesResponse::new);

    }
}
