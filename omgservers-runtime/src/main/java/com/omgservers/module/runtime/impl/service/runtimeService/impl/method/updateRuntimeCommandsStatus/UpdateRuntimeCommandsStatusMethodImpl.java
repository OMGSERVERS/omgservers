package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.updateRuntimeCommandsStatus;

import com.omgservers.dto.runtime.UpdateRuntimeCommandsStatusRequest;
import com.omgservers.dto.runtime.UpdateRuntimeCommandsStatusResponse;
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
class UpdateRuntimeCommandsStatusMethodImpl implements UpdateRuntimeCommandsStatusMethod {

    final UpdateRuntimeCommandStatusByIdsOperation updateRuntimeCommandStatusByIdsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<UpdateRuntimeCommandsStatusResponse> updateRuntimeCommandsStatus(final UpdateRuntimeCommandsStatusRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var runtimeId = request.getRuntimeId();
                    final var ids = request.getIds();
                    final var status = request.getStatus();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                    updateRuntimeCommandStatusByIdsOperation.updateRuntimeCommandStatusByIds(
                                            changeContext,
                                            sqlConnection,
                                            shardModel.shard(),
                                            runtimeId,
                                            ids,
                                            status))
                            .map(ChangeContext::getResult);
                })
                .map(UpdateRuntimeCommandsStatusResponse::new);

    }
}
