package com.omgservers.module.tenant.impl.service.versionService.impl.method.findStageVersionId;

import com.omgservers.dto.tenant.FindStageVersionIdRequest;
import com.omgservers.dto.tenant.FindStageVersionIdResponse;
import com.omgservers.module.tenant.impl.operation.selectVersionIdByStageId.SelectVersionIdByStageIdOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindStageVersionIdMethodImpl implements FindStageVersionIdMethod {

    final SelectVersionIdByStageIdOperation selectVersionIdByStageIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindStageVersionIdResponse> findStageVersionId(FindStageVersionIdRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var stageId = request.getStageId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionIdByStageIdOperation
                            .selectVersionIdByStageId(sqlConnection, shardModel.shard(), tenantId, stageId));
                })
                .map(FindStageVersionIdResponse::new);
    }
}
