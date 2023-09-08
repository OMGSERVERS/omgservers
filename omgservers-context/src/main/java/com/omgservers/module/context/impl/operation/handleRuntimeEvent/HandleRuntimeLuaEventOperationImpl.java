package com.omgservers.module.context.impl.operation.handleRuntimeEvent;

import com.omgservers.base.cache.InMemoryCache;
import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import com.omgservers.module.context.impl.operation.createLuaGlobals.CreateLuaGlobalsOperation;
import com.omgservers.module.context.impl.operation.createLuaInstance.CreateLuaInstanceOperation;
import com.omgservers.module.context.impl.operation.createLuaInstance.impl.LuaInstance;
import com.omgservers.module.context.impl.operation.createLuaRuntimeContext.CreateLuaRuntimeContextOperation;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleRuntimeLuaEventOperationImpl implements HandleRuntimeLuaEventOperation {

    final CreateLuaRuntimeContextOperation createLuaRuntimeContextOperation;
    final CreateLuaInstanceOperation createLuaInstanceOperation;
    final CreateLuaGlobalsOperation createLuaGlobalsOperation;

    final LuaInstanceCache cache;

    @Override
    public Uni<Boolean> handleRuntimeLuaEvent(final Long tenantId,
                                              final Long versionId,
                                              final Long matchmakerId,
                                              final Long matchId,
                                              final Long runtimeId,
                                              final LuaEvent luaEvent) {
        final var cacheKey = new CacheKey(runtimeId);
        return Uni.createFrom().item(cache.hasCacheFor(cacheKey))
                .flatMap(hasCachedValue -> {
                    if (hasCachedValue) {
                        return Uni.createFrom().item(cache.getValue(cacheKey));
                    } else {
                        return createLuaInstance(tenantId, versionId, matchmakerId, matchId, runtimeId)
                                .invoke(cacheValue -> cache.cacheIfAbsent(cacheKey, cacheValue));
                    }
                })
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .map(luaInstance -> luaInstance.handleEvent(luaEvent));
    }

    Uni<LuaInstance> createLuaInstance(final Long tenantId,
                                       final Long versionId,
                                       final Long matchmakerId,
                                       final Long matchId,
                                       final Long runtimeId) {
        return createLuaGlobalsOperation.createLuaGlobals(
                        tenantId,
                        versionId)
                .flatMap(luaGlobals -> createLuaRuntimeContextOperation.createLuaRuntimeContext(
                                matchmakerId,
                                matchId,
                                runtimeId)
                        .flatMap(luaRuntimeContext -> createLuaInstanceOperation.createLuaInstance(
                                luaGlobals,
                                luaRuntimeContext)));
    }

    @ApplicationScoped
    static class LuaInstanceCache extends InMemoryCache<CacheKey, LuaInstance> {
    }

    record CacheKey(Long runtimeId) {
    }
}
