package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.findRuntimeClient;

import com.omgservers.model.dto.runtime.FindRuntimeClientRequest;
import com.omgservers.model.dto.runtime.FindRuntimeClientResponse;
import com.omgservers.service.module.runtime.impl.operation.selectRuntimeClientByRuntimeIdAndClientId.SelectRuntimeClientByRuntimeIdAndClientIdOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class FindRuntimeClientMethodImpl implements FindRuntimeClientMethod {

    final SelectRuntimeClientByRuntimeIdAndClientIdOperation selectRuntimeClientByRuntimeIdAndClientIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<FindRuntimeClientResponse> findRuntimeClient(final FindRuntimeClientRequest request) {
        log.debug("Find runtime client, request={}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var entityId = request.getClientId();
                    return pgPool.withTransaction(sqlConnection -> selectRuntimeClientByRuntimeIdAndClientIdOperation
                            .selectRuntimeClientByRuntimeIdAndEntityId(sqlConnection,
                                    shard.shard(),
                                    runtimeId,
                                    entityId));
                })
                .map(FindRuntimeClientResponse::new);
    }
}
