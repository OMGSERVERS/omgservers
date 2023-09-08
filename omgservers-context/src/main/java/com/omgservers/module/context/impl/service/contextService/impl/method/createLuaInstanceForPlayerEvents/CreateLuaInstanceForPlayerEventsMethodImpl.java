package com.omgservers.module.context.impl.service.contextService.impl.method.createLuaInstanceForPlayerEvents;

import com.omgservers.dto.context.CreateLuaInstanceForPlayerEventsRequest;
import com.omgservers.dto.context.CreateLuaInstanceForPlayerEventsResponse;
import com.omgservers.module.context.impl.operation.createLuaGlobals.CreateLuaGlobalsOperation;
import com.omgservers.module.context.impl.operation.createLuaInstance.CreateLuaInstanceOperation;
import com.omgservers.module.context.impl.operation.createLuaPlayerContext.CreateLuaPlayerContextOperation;
import com.omgservers.module.context.impl.service.contextService.impl.cache.LuaInstanceCache;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class CreateLuaInstanceForPlayerEventsMethodImpl implements CreateLuaInstanceForPlayerEventsMethod {

    final CreateLuaPlayerContextOperation createLuaPlayerContextOperation;
    final CreateLuaInstanceOperation createLuaInstanceOperation;
    final CreateLuaGlobalsOperation createLuaGlobalsOperation;

    final LuaInstanceCache luaInstanceCache;

    @Override
    public Uni<CreateLuaInstanceForPlayerEventsResponse> createLuaInstanceForPlayerEvents(
            final CreateLuaInstanceForPlayerEventsRequest request) {
        final var tenantId = request.getTenantId();
        final var versionId = request.getVersionId();
        final var userId = request.getUserId();
        final var playerId = request.getPlayerId();
        final var clientId = request.getClientId();

        final var cacheKey = clientId;

        if (luaInstanceCache.hasCacheFor(cacheKey)) {
            return Uni.createFrom().item(new CreateLuaInstanceForPlayerEventsResponse(false));
        } else {
            return createLuaGlobalsOperation.createLuaGlobals(tenantId, versionId)
                    .flatMap(luaGlobals -> createLuaPlayerContextOperation.createLuaPlayerContext(
                                    userId,
                                    playerId,
                                    clientId)
                            .flatMap(luaRuntimeContext -> createLuaInstanceOperation.createLuaInstance(
                                    luaGlobals,
                                    luaRuntimeContext)))
                    .map(luaInstance -> luaInstanceCache.cacheIfAbsent(cacheKey, luaInstance))
                    .replaceWith(CreateLuaInstanceForPlayerEventsResponse::new);
        }
    }
}
