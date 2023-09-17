package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.markRuntimeCommands;

import com.omgservers.dto.runtime.MarkRuntimeCommandsRequest;
import com.omgservers.dto.runtime.MarkRuntimeCommandsResponse;
import com.omgservers.module.runtime.impl.operation.updateRuntimeCommandsStatusByIds.UpdateRuntimeCommandStatusByIdsOperation;
import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class MarkRuntimeCommandsMethodImpl implements MarkRuntimeCommandsMethod {

    final UpdateRuntimeCommandStatusByIdsOperation runtimeCommandStatusByIdsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<MarkRuntimeCommandsResponse> markRuntimeCommands(final MarkRuntimeCommandsRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var runtimeId = request.getRuntimeId();
                    final var ids = request.getIds();
                    final var status = request.getStatus();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                    runtimeCommandStatusByIdsOperation.updateRuntimeCommandStatusByIds(
                                            changeContext,
                                            sqlConnection,
                                            shardModel.shard(),
                                            runtimeId,
                                            ids,
                                            status))
                            .map(ChangeContext::getResult);
                })
                .map(MarkRuntimeCommandsResponse::new);

    }
}
