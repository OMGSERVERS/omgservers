package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.doUpdateMethod;

import com.omgservers.application.module.runtimeModule.RuntimeModule;
import com.omgservers.operation.checkShard.CheckShardOperation;
import com.omgservers.dto.runtimeModule.DoUpdateShardRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeShardRequest;
import com.omgservers.dto.runtimeModule.GetRuntimeInternalResponse;
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
    public Uni<Void> doUpdate(DoUpdateShardRequest request) {
        DoUpdateShardRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    return getRuntime(runtimeId)
                            .flatMap(runtime -> Uni.createFrom().voidItem());
                });
    }

    Uni<RuntimeModel> getRuntime(Long id) {
        final var request = new GetRuntimeShardRequest(id);
        return runtimeModule.getRuntimeInternalService().getRuntime(request)
                .map(GetRuntimeInternalResponse::getRuntime);
    }
}
