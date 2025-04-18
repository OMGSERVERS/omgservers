package com.omgservers.service.shard.runtime.impl.service.runtimeService.impl.method.runtime;

import com.omgservers.schema.model.shard.ShardModel;
import com.omgservers.schema.shard.runtime.runtime.SyncRuntimeRequest;
import com.omgservers.schema.shard.runtime.runtime.SyncRuntimeResponse;
import com.omgservers.service.operation.server.ChangeContext;
import com.omgservers.service.operation.server.ChangeWithContextOperation;
import com.omgservers.service.shard.runtime.impl.operation.runtime.UpsertRuntimeOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class SyncRuntimeMethodImpl implements SyncRuntimeMethod {

    final ChangeWithContextOperation changeWithContextOperation;
    final UpsertRuntimeOperation upsertRuntimeOperation;

    @Override
    public Uni<SyncRuntimeResponse> execute(final ShardModel shardModel,
                                            final SyncRuntimeRequest request) {
        log.trace("{}", request);

        final var runtime = request.getRuntime();
        return changeWithContextOperation.<Boolean>changeWithContext((changeContext, sqlConnection) ->
                        upsertRuntimeOperation.execute(changeContext, sqlConnection, shardModel.slot(), runtime))
                .map(ChangeContext::getResult)
                .map(SyncRuntimeResponse::new);
    }
}
