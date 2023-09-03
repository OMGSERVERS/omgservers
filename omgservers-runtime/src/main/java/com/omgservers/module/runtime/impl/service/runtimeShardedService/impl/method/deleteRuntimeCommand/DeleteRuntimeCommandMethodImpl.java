package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.deleteRuntimeCommand;

import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeCommandShardedResponse;
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
    public Uni<DeleteRuntimeCommandShardedResponse> deleteRuntimeCommand(DeleteRuntimeCommandShardedRequest request) {
        DeleteRuntimeCommandShardedRequest.validate(request);

        final var runtimeId = request.getRuntimeId();
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, runtimeId, id))
                .map(DeleteRuntimeCommandShardedResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long runtimeId, Long id) {
        return changeWithContextOperation.changeWithContext((changeContext, sqlConnection) ->
                deleteRuntimeCommandOperation.deleteRuntimeCommand(
                        changeContext,
                        sqlConnection,
                        shardModel.shard(),
                        runtimeId,
                        id));
    }
}
