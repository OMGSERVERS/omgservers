package com.omgservers.module.version.impl.service.versionShardedService.impl.method.getStageConfig;

import com.omgservers.module.version.impl.operation.selectStageConfig.SelectStageConfigOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.version.GetStageConfigShardedRequest;
import com.omgservers.dto.version.GetStageConfigShardedResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetStageConfigMethodImpl implements GetStageConfigMethod {

    final CheckShardOperation checkShardOperation;
    final SelectStageConfigOperation selectStageConfigOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetStageConfigShardedResponse> getStageConfig(GetStageConfigShardedRequest request) {
        GetStageConfigShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var versionId = request.getVersionId();
                    return pgPool.withTransaction(sqlConnection -> selectStageConfigOperation
                            .selectStageConfig(sqlConnection, shard, versionId));
                })
                .map(GetStageConfigShardedResponse::new);
    }
}
