package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.getRuntime;

import com.omgservers.model.dto.runtime.GetRuntimeRequest;
import com.omgservers.model.dto.runtime.GetRuntimeResponse;
import com.omgservers.service.module.runtime.impl.operation.selectRuntime.SelectRuntimeOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    public Uni<GetRuntimeResponse> getRuntime(GetRuntimeRequest request) {
        log.debug("Get runtime, request={}", request);
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var id = request.getId();
                    return pgPool.withTransaction(sqlConnection -> selectRuntimeOperation
                            .selectRuntime(sqlConnection, shard.shard(), id));
                })
                .map(GetRuntimeResponse::new);
    }
}
