package com.omgservers.application.module.versionModule.impl.service.versionInternalService.impl.method.getVersionMethod;

import com.omgservers.application.module.versionModule.impl.operation.selectVersionOperation.SelectVersionOperation;
import com.omgservers.base.impl.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.dto.versionModule.GetVersionInternalRequest;
import com.omgservers.dto.versionModule.GetVersionInternalResponse;
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
    public Uni<GetVersionInternalResponse> getVersion(GetVersionInternalRequest request) {
        GetVersionInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var shard = shardModel.shard();
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectVersionOperation
                            .selectVersion(sqlConnection, shard, id));
                })
                .map(GetVersionInternalResponse::new);
    }
}
