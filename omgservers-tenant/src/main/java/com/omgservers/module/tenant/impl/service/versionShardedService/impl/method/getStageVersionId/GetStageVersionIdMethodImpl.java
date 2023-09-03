package com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.getStageVersionId;

import com.omgservers.dto.tenant.GetStageVersionIdShardedRequest;
import com.omgservers.dto.tenant.GetStageVersionIdShardedResponse;
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
    public Uni<GetStageVersionIdShardedResponse> getStageVersionId(GetStageVersionIdShardedRequest request) {
        GetStageVersionIdShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var stageId = request.getStageId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionIdByStageIdOperation
                            .selectVersionIdByStageId(sqlConnection, shardModel.shard(), stageId));
                })
                .map(GetStageVersionIdShardedResponse::new);
    }
}
