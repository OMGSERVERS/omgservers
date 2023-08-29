package com.omgservers.module.context.impl.service.contextService.impl.method.handlePlayerSignedUpEvent;

import com.omgservers.dto.context.HandlePlayerSignedUpEventRequest;
import com.omgservers.dto.context.HandlePlayerSignedUpEventResponse;
import com.omgservers.module.context.impl.operation.createLuaGlobals.CreateLuaGlobalsOperation;
import com.omgservers.module.context.impl.operation.createLuaGlobals.impl.LuaGlobals;
import com.omgservers.module.context.impl.luaEvent.LuaEvent;
import com.omgservers.module.context.impl.luaEvent.player.LuaPlayerSignedUpEvent;
import com.omgservers.module.context.impl.operation.createLuaPlayerContext.CreateLuaPlayerContextOperation;
import com.omgservers.module.context.impl.operation.createLuaPlayerContext.impl.LuaPlayerContext;
import com.omgservers.module.lua.impl.service.luaService.LuaService;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandlePlayerSignedUpEventMethodImpl implements HandlePlayerSignedUpEventMethod {

    final LuaService luaService;

    final CreateLuaGlobalsOperation createLuaGlobalsOperation;
    final CreateLuaPlayerContextOperation createLuaPlayerContextOperation;

    @Override
    public Uni<HandlePlayerSignedUpEventResponse> handleLuaPlayerSignedUpEvent(final HandlePlayerSignedUpEventRequest request) {
        HandlePlayerSignedUpEventRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var userId = request.getUserId();
        final var playerId = request.getPlayerId();
        final var clientId = request.getClientId();

        return createLuaGlobalsOperation.createLuaGlobals(tenantId, stageId)
                .flatMap(luaGlobals -> createLuaPlayerContextOperation.createLuaPlayerContext(userId, playerId, clientId)
                        .flatMap(luaPlayerContext -> {
                            final var luaEvent = new LuaPlayerSignedUpEvent(userId, playerId, clientId);
                            return handleEvent(luaGlobals, luaEvent, luaPlayerContext);
                        }))
                .replaceWith(new HandlePlayerSignedUpEventResponse(true));
    }

    Uni<Void> handleEvent(final LuaGlobals luaGlobals,
                          final LuaEvent luaEvent,
                          final LuaPlayerContext luaPlayerContext) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> luaGlobals.handleEvent(luaEvent, luaPlayerContext));
    }
}
