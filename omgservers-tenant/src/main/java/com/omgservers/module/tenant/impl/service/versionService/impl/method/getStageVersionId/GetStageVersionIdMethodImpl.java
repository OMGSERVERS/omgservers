package com.omgservers.module.tenant.impl.service.versionService.impl.method.getStageVersionId;

import com.omgservers.dto.tenant.GetStageVersionIdRequest;
import com.omgservers.dto.tenant.GetStageVersionIdResponse;
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
class GetStageVersionIdMethodImpl implements GetStageVersionIdMethod {

    final SelectVersionIdByStageIdOperation selectVersionIdByStageIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetStageVersionIdResponse> getStageVersionId(GetStageVersionIdRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var tenantId = request.getTenantId();
                    final var stageId = request.getStageId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionIdByStageIdOperation
                            .selectVersionIdByStageId(sqlConnection, shardModel.shard(), tenantId, stageId));
                })
                .map(GetStageVersionIdResponse::new);
    }
}
