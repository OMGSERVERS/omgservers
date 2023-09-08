package com.omgservers.module.context.impl.operation.handleRuntimeLuaEvent;

import com.omgservers.exception.ServerSideConflictException;
import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import com.omgservers.module.context.impl.service.contextService.impl.cache.LuaInstanceCache;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleRuntimeLuaEventOperationImpl implements HandleRuntimeLuaEventOperation {

    final LuaInstanceCache cache;

    @Override
    public Uni<Boolean> handleRuntimeLuaEvent(final Long runtimeId, final LuaEvent luaEvent) {
        final var cacheKey = runtimeId;
        return Uni.createFrom().item(cache.getValue(cacheKey))
                .onItem().ifNull().failWith(new ServerSideConflictException("runtime lua instance was not created for, " +
                        "runtimeId=" + runtimeId))
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(luaInstance -> luaInstance.handleEvent(luaEvent));
    }
}
