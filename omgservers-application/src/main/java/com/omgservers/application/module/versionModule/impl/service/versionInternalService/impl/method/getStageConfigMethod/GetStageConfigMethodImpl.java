package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getStageConfigMethod;

import com.omgservers.application.module.versionModule.impl.operation.selectStageConfigOperation.SelectStageConfigOperation;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.request.GetStageConfigInternalRequest;
import com.omgservers.application.module.versionModule.impl.service.versionInternalService.response.GetStageConfigInternalResponse;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
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
    public Uni<GetStageConfigInternalResponse> getStageConfig(GetStageConfigInternalRequest request) {
        GetStageConfigInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var versionId = request.getVersionId();
                    return pgPool.withTransaction(sqlConnection -> selectStageConfigOperation
                            .selectStageConfig(sqlConnection, shard, versionId));
                })
                .map(GetStageConfigInternalResponse::new);
    }
}
