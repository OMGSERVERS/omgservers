package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.deleteRuntime;

import com.omgservers.dto.runtime.DeleteRuntimeRequest;
import com.omgservers.dto.runtime.DeleteRuntimeResponse;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.runtime.impl.operation.deleteRuntime.DeleteRuntimeOperation;
import com.omgservers.module.system.SystemModule;
import com.omgservers.operation.changeWithContext.ChangeContext;
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

    final SystemModule systemModule;

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteRuntimeOperation deleteRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteRuntimeResponse> deleteRuntime(DeleteRuntimeRequest request) {
        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, id))
                .map(DeleteRuntimeResponse::new);
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
