package com.omgservers.module.tenant.impl.service.stageShardedService.impl.method.getStage;

import com.omgservers.module.tenant.impl.operation.selectStage.SelectStageOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.tenant.GetStageShardedRequest;
import com.omgservers.dto.tenant.GetStageInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetStageMethodImpl implements GetStageMethod {

    final SelectStageOperation selectStageOperation;
    final CheckShardOperation checkShardOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetStageInternalResponse> getStage(final GetStageShardedRequest request) {
        GetStageShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectStageOperation
                            .selectStage(sqlConnection, shardModel.shard(), id));
                })
                .map(GetStageInternalResponse::new);
    }
}
