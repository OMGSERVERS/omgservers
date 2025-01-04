package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtimeCommand;

import com.omgservers.schema.module.runtime.DeleteRuntimeCommandsRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeCommandsResponse;
import com.omgservers.service.module.runtime.impl.operation.runtimeCommand.DeleteRuntimeCommandByIdsOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
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
    public Uni<DeleteRuntimeCommandsResponse> execute(final DeleteRuntimeCommandsRequest request) {
        log.trace("{}", request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shardModel -> {
                    final var runtimeId = request.getRuntimeId();
                    final var ids = request.getIds();
                    return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                                    deleteRuntimeCommandByIdsOperation.execute(changeContext,
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
