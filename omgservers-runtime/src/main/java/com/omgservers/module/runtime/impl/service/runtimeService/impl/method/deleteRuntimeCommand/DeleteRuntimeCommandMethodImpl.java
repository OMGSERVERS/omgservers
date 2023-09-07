package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntimeCommand;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.runtime.DeleteRuntimeCommandRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.runtime.impl.operation.deleteRuntimeCommand.DeleteRuntimeCommandOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRuntimeCommandMethodImpl implements DeleteRuntimeCommandMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteRuntimeCommandOperation deleteRuntimeCommandOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteRuntimeCommandResponse> deleteRuntimeCommand(DeleteRuntimeCommandRequest request) {
        DeleteRuntimeCommandRequest.validate(request);

        final var runtimeId = request.getRuntimeId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, runtimeId, id))
                .map(DeleteRuntimeCommandResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long runtimeId, Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteRuntimeCommandOperation.deleteRuntimeCommand(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                runtimeId,
                                id))
                .map(ChangeContext::getResult);
    }
}
