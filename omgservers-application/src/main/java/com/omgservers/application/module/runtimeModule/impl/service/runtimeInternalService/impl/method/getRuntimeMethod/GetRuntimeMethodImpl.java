package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.getRuntimeMethod;

import com.omgservers.application.module.runtimeModule.impl.operation.selectRuntimeOperation.SelectRuntimeOperation;
import com.omgservers.base.impl.operation.checkShardOperation.CheckShardOperation;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalResponse;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class GetRuntimeMethodImpl implements GetRuntimeMethod {

    final SelectRuntimeOperation selectRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<GetRuntimeInternalResponse> getRuntime(GetRuntimeInternalRequest request) {
        GetRuntimeInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectRuntimeOperation
                            .selectRuntime(sqlConnection, shard.shard(), id));
                })
                .map(GetRuntimeInternalResponse::new);
    }
}
