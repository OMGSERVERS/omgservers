package com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.impl.method.doUpdateMethod;

import com.omgservers.application.module.runtimeModule.RuntimeModule;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.DoUpdateInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.request.GetRuntimeInternalRequest;
import com.omgservers.application.module.runtimeModule.impl.service.runtimeInternalService.response.GetRuntimeInternalResponse;
import com.omgservers.application.module.runtimeModule.model.runtime.RuntimeModel;
import com.omgservers.application.operation.checkShardOperation.CheckShardOperation;
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
    public Uni<Void> doUpdate(DoUpdateInternalRequest request) {
        DoUpdateInternalRequest.validate(request);

        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> {
                    final var runtimeId = request.getRuntimeId();
                    return getRuntime(runtimeId)
                            .flatMap(runtime -> Uni.createFrom().voidItem());
                });
    }

    Uni<RuntimeModel> getRuntime(Long id) {
        final var request = new GetRuntimeInternalRequest(id);
        return runtimeModule.getRuntimeInternalService().getRuntime(request)
                .map(GetRuntimeInternalResponse::getRuntime);
    }
}
