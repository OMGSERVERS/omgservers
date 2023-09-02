package com.omgservers.module.tenant.impl.service.versionShardedService.impl.method.getCurrentVersionId;

import com.omgservers.dto.tenant.GetCurrentVersionIdShardedRequest;
import com.omgservers.dto.tenant.GetCurrentVersionIdShardedResponse;
import com.omgservers.module.tenant.impl.operation.selectCurrentVersionId.SelectCurrentVersionIdOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetCurrentVersionIdMethodImpl implements GetCurrentVersionIdMethod {

    final SelectCurrentVersionIdOperation selectCurrentVersionIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetCurrentVersionIdShardedResponse> getCurrentVersionId(GetCurrentVersionIdShardedRequest request) {
        GetCurrentVersionIdShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var stageId = request.getStageId();
                    return pgPool.withTransaction(sqlConnection -> selectCurrentVersionIdOperation
                            .selectCurrentVersionId(sqlConnection, shardModel.shard(), stageId));
                })
                .map(GetCurrentVersionIdShardedResponse::new);
    }
}
