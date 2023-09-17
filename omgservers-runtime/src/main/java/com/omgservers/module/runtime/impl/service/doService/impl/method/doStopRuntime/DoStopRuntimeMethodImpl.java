package com.omgservers.module.runtime.impl.service.doService.impl.method.doStopRuntime;

import com.omgservers.dto.runtime.DoStopRuntimeRequest;
import com.omgservers.dto.runtime.DoStopRuntimeResponse;
import com.omgservers.operation.checkShard.CheckShardOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class DoStopRuntimeMethodImpl implements DoStopRuntimeMethod {

    final CheckShardOperation checkShardOperation;

    @Override
    public Uni<DoStopRuntimeResponse> doStopRuntime(final DoStopRuntimeRequest request) {
        return checkShardOperation.checkShard(request.getRequestShardKey())
                .flatMap(shard -> Uni.createFrom().voidItem())
                .replaceWith(new DoStopRuntimeResponse());
    }
}
