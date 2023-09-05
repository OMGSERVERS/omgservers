package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.deleteRuntime;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.runtime.DeleteRuntimeShardedRequest;
import com.omgservers.dto.runtime.DeleteRuntimeShardedResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.internal.InternalModule;
import com.omgservers.module.runtime.impl.operation.deleteRuntime.DeleteRuntimeOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRuntimeMethodImpl implements DeleteRuntimeMethod {

    final InternalModule internalModule;

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteRuntimeOperation deleteRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteRuntimeShardedResponse> deleteRuntime(DeleteRuntimeShardedRequest request) {
        DeleteRuntimeShardedRequest.validate(request);

        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, id))
                .map(DeleteRuntimeShardedResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteRuntimeOperation.deleteRuntime(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                id))
                .map(ChangeContext::getResult);
    }
}
