package com.omgservers.module.version.impl.service.versionShardedService.impl.method.getVersion;

import com.omgservers.module.version.impl.operation.selectVersion.SelectVersionOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.version.GetVersionShardedRequest;
import com.omgservers.dto.version.GetVersionShardedResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetVersionMethodImpl implements GetVersionMethod {

    final CheckShardOperation checkShardOperation;
    final SelectVersionOperation selectVersionOperation;
    final PgPool pgPool;

    @Override
    public Uni<GetVersionShardedResponse> getVersion(GetVersionShardedRequest request) {
        GetVersionShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionOperation
                            .selectVersion(sqlConnection, shard, id));
                })
                .map(GetVersionShardedResponse::new);
    }
}
