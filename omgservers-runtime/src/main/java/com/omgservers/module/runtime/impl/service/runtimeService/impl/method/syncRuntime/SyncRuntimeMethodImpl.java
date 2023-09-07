package com.omgservers.module.runtime.impl.service.runtimeService.impl.method.syncRuntime;

import com.omgservers.operation.changeWithContext.ChangeContext;
import com.omgservers.dto.runtime.SyncRuntimeRequest;
import com.omgservers.dto.runtime.SyncRuntimeResponse;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.shard.ShardModel;
import com.omgservers.module.runtime.impl.operation.upsertRuntime.UpsertRuntimeOperation;
import com.omgservers.operation.changeWithContext.ChangeWithContextOperation;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncRuntimeMethodImpl implements SyncRuntimeMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertRuntimeOperation upsertRuntimeOperation;
    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<SyncRuntimeResponse> syncRuntime(SyncRuntimeRequest request) {
        SyncRuntimeRequest.validate(request);

        final var runtime = request.getRuntime();
        return Uni.createFrom().voidItem()
                .flatMap(voidItem -> checkShardOperation.checkShard(request.getRequestShardKey()))
                .flatMap(shardModel -> changeFunction(shardModel, runtime))
                .map(SyncRuntimeResponse::new);
    }

    Uni<Boolean> changeFunction(ShardModel shardModel, RuntimeModel runtime) {
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertRuntimeOperation.upsertRuntime(
                                changeContext,
                                sqlConnection,
                                shardModel.shard(),
                                runtime))
                .map(ChangeContext::getResult);
    }
}
