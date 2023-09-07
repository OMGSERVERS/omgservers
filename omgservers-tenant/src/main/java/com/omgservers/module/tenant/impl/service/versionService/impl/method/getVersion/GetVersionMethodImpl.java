package com.omgservers.module.tenant.impl.service.versionService.impl.method.getVersion;

import com.omgservers.dto.tenant.GetVersionRequest;
import com.omgservers.dto.tenant.GetVersionResponse;
import com.omgservers.module.tenant.impl.operation.selectVersion.SelectVersionOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetVersionMethodImpl implements GetVersionMethod {

    final SelectVersionOperation selectVersionOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetVersionResponse> getVersion(GetVersionRequest request) {
        GetVersionRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionOperation
                            .selectVersion(sqlConnection, shard, id));
                })
                .map(GetVersionResponse::new);
    }
}
