package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.viewRuntimeCommands;

import com.omgservers.dto.runtime.ViewRuntimeCommandsRequest;
import com.omgservers.dto.runtime.ViewRuntimeCommandsResponse;
import com.omgservers.module.runtime.impl.operation.selectRuntimeCommandsByRuntimeId.SelectRuntimeCommandsByRuntimeIdOperation;
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

    final SelectRuntimeCommandsByRuntimeIdOperation selectRuntimeCommandsByRuntimeIdOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<ViewRuntimeCommandsResponse> viewRuntimeCommands(ViewRuntimeCommandsRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    final var deleted = request.getDeleted();
                    return pgPool.withTransaction(sqlConnection -> selectRuntimeCommandsByRuntimeIdOperation
                            .selectRuntimeCommandsByRuntimeId(sqlConnection,
                                    shard.shard(),
                                    runtimeId,
                                    deleted));
                })
                .map(ViewRuntimeCommandsResponse::new);

    }
}
