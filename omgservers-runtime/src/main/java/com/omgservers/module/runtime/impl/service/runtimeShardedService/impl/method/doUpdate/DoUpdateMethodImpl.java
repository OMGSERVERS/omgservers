package com.omgservers.module.runtime.impl.service.runtimeShardedService.impl.method.doUpdate;

import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.runtime.DoUpdateShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedResponse;
import com.omgservers.model.runtime.RuntimeModel;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoUpdateMethodImpl implements DoUpdateMethod {

    final RuntimeModule runtimeModule;

    final CheckShardOperation checkShardOperation;

    final PgPool pgPool;

    @Override
    public Uni<Void> doUpdate(DoUpdateShardedRequest request) {
        DoUpdateShardedRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    return getRuntime(runtimeId)
                            .flatMap(runtime -> Uni.createFrom().voidItem());
                });
    }

    Uni<RuntimeModel> getRuntime(Long id) {
        final var request = new GetRuntimeShardedRequest(id);
        return runtimeModule.getRuntimeShardedService().getRuntime(request)
                .map(GetRuntimeShardedResponse::getRuntime);
    }
}
