package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.viewRuntimeCommands;

import com.omgservers.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.module.runtime.impl.operation.selectRuntimeCommandsByRuntimeIdAndStatus.SelectRuntimeCommandsByRuntimeIdAndStatusOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ViewRuntimeCommandsMethodImpl implements ViewRuntimeCommandsMethod {

    final SelectRuntimeCommandsByRuntimeIdAndStatusOperation selectRuntimeCommandsByRuntimeIdAndStatusOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(ViewRuntimeCommandsRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var status = request.getStatus();
                    return pgPool.withTransaction(sqlConnection -> selectRuntimeCommandsByRuntimeIdAndStatusOperation
                            .selectRuntimeCommandsByRuntimeIdAndStatus(sqlConnection, shard.shard(), runtimeId,
                                    status));
                })
                .map(ViewRuntimeCommandsResponse::new);

    }
}
