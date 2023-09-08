package com.omgservers.module.context.impl.service.contextService.impl.method.createLuaInstanceForRuntimeEvents;

import com.omgservers.dto.context.CreateLuaInstanceForRuntimeEventsRequest;
import com.omgservers.dto.context.CreateLuaInstanceForRuntimeEventsResponse;
import com.omgservers.module.context.impl.operation.createLuaGlobals.CreateLuaGlobalsOperation;
import com.omgservers.module.context.impl.operation.createLuaInstance.CreateLuaInstanceOperation;
import com.omgservers.module.context.impl.operation.createLuaRuntimeContext.CreateLuaRuntimeContextOperation;
import com.omgservers.module.context.impl.service.contextService.impl.cache.LuaInstanceCache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateLuaInstanceForRuntimeEventsMethodImpl implements CreateLuaInstanceForRuntimeEventsMethod {

    final CreateLuaRuntimeContextOperation createLuaRuntimeContextOperation;
    final CreateLuaInstanceOperation createLuaInstanceOperation;
    final CreateLuaGlobalsOperation createLuaGlobalsOperation;

    final LuaInstanceCache luaInstanceCache;

    @Override
    public Uni<CreateLuaInstanceForRuntimeEventsResponse> createLuaInstanceForRuntimeEvents(
            final CreateLuaInstanceForRuntimeEventsRequest request) {
        final var runtime = request.getRuntime();

        final var tenantId = runtime.getTenantId();
        final var versionId = runtime.getVersionId();
        final var matchmakerId = runtime.getMatchmakerId();
        final var matchId = runtime.getMatchId();
        final var runtimeId = runtime.getId();

        final var cacheKey = runtimeId;

        if (luaInstanceCache.hasCacheFor(cacheKey)) {
            return Uni.createFrom().item(new CreateLuaInstanceForRuntimeEventsResponse(false));
        } else {
            return createLuaGlobalsOperation.createLuaGlobals(tenantId, versionId)
                    .flatMap(luaGlobals -> createLuaRuntimeContextOperation.createLuaRuntimeContext(
                                    matchmakerId,
                                    matchId,
                                    runtimeId)
                            .flatMap(luaRuntimeContext -> createLuaInstanceOperation.createLuaInstance(
                                    luaGlobals,
                                    luaRuntimeContext)))
                    .map(luaInstance -> luaInstanceCache.cacheIfAbsent(cacheKey, luaInstance))
                    .replaceWith(CreateLuaInstanceForRuntimeEventsResponse::new);
        }
    }
}
