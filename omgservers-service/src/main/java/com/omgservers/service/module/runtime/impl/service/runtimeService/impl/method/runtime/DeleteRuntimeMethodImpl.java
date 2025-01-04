package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.module.runtime.DeleteRuntimeRequest;
import com.omgservers.schema.module.runtime.DeleteRuntimeResponse;
import com.omgservers.service.module.runtime.impl.operation.runtime.DeleteRuntimeOperation;
import com.omgservers.service.operation.changeWithContext.ChangeContext;
import com.omgservers.service.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.service.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class DeleteRuntimeMethodImpl implements DeleteRuntimeMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final DeleteRuntimeOperation deleteRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DeleteRuntimeResponse> execute(DeleteRuntimeRequest request) {
        log.trace("{}", request);

        final var id = request.getId();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, id))
                .map(DeleteRuntimeResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, Long id) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        deleteRuntimeOperation.execute(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                id))
                .map(ChangeContext::getResult);
    }
}
