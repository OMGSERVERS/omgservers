package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeCommands;

import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.model.dto.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.module.runtime.impl.operation.deleteRuntimeCommandsByIds.DeleteRuntimeCommandByIdsOperation;
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
class DeleteRuntimeCommandsMethodImpl implements DeleteRuntimeCommandsMethod {

    final DeleteRuntimeCommandByIdsOperation deleteRuntimeCommandByIdsOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteRuntimeCommandsResponse> deleteRuntimeCommands(final DeleteRuntimeCommandsRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var runtimeId = request.getRuntimeId();
                    final var ids = request.getIds();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                    deleteRuntimeCommandByIdsOperation.deleteRuntimeCommandByIds(changeContext,
                                            sqlConnection,
                                            shardModel.shard(),
                                            runtimeId,
                                            ids
                                    ))
                            .map(ChangeContext::getResult);
                })
                .map(DeleteRuntimeCommandsResponse::new);

    }
}
