package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntime;

import com.omgservers.dto.runtime.SyncRuntimeRequest;
import com.omgservers.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.runtime.factory.RuntimeCommandModelFactory;
import com.omgservers.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
import com.omgservers.module.runtime.impl.operation.upsertRuntimeCommand.UpsertRuntimeCommandOperation;
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
class SyncRuntimeMethodImpl implements SyncRuntimeMethod {

    final UpsertRuntimeCommandOperation upsertRuntimeCommandOperation;
    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertRuntimeOperation upsertRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    final RuntimeCommandModelFactory runtimeCommandModelFactory;

    @Override
    public Uni<SyncRuntimeResponse> syncRuntime(SyncRuntimeRequest request) {
        final var runtime = request.getRuntime();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, runtime))
                .map(SyncRuntimeResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, RuntimeModel runtime) {
        final int shard = shardModel.shard();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertRuntimeOperation.upsertRuntime(
                                        changeContext,
                                        sqlConnection,
                                        shard,
                                        runtime)
                                .call(runtimeWasInserted -> {
                                    if (runtimeWasInserted) {
                                        // InitRuntime is always first command of runtime
                                        final var commandBody = InitRuntimeCommandBodyModel.builder()
                                                .config(runtime.getConfig())
                                                .build();
                                        final var initCommand = runtimeCommandModelFactory
                                                .create(runtime.getId(), commandBody);
                                        return upsertRuntimeCommandOperation.upsertRuntimeCommand(
                                                changeContext,
                                                sqlConnection,
                                                shard,
                                                initCommand);
                                    } else {
                                        return Uni.createFrom().voidItem();
                                    }
                                })
                )
                .map(ChangeContext::getResult);
    }
}
