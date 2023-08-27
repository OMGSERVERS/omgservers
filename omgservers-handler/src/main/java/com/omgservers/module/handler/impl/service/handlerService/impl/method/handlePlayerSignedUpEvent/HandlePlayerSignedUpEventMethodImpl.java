package com.omgservers.module.handler.impl.service.handlerService.impl.method.handlePlayerSignedUpEvent;

import com.omgservers.dto.handler.HandlePlayerSignedUpEventRequest;
import com.omgservers.module.handler.impl.operation.createLuaRuntime.CreateLuaRuntimeOperation;
import com.omgservers.module.handler.impl.operation.createLuaRuntime.impl.LuaRuntime;
import com.omgservers.module.handler.impl.operation.createLuaRuntime.impl.event.LuaEvent;
import com.omgservers.module.handler.impl.operation.createLuaRuntime.impl.event.LuaPlayerSignedUpEvent;
import com.omgservers.module.handler.impl.operation.createPlayerContext.CreateLuaPlayerContextOperation;
import com.omgservers.module.handler.impl.operation.createPlayerContext.impl.LuaPlayerContext;
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

    final CreateLuaRuntimeOperation createLuaRuntimeOperation;
    final CreateLuaPlayerContextOperation createLuaPlayerContextOperation;

    @Override
    public Uni<Void> handleLuaPlayerSignedUpEvent(final HandlePlayerSignedUpEventRequest request) {
        HandlePlayerSignedUpEventRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var userId = request.getUserId();
        final var playerId = request.getPlayerId();
        final var clientId = request.getClientId();

        return createLuaRuntimeOperation.createLuaRuntime(tenantId, stageId)
                .flatMap(luaRuntime -> createLuaPlayerContextOperation.createLuaPlayerContext(userId, playerId, clientId)
                        .flatMap(luaPlayerContext -> {
                            final var luaEvent = new LuaPlayerSignedUpEvent(userId, playerId, clientId);
                            return handleEvent(luaRuntime, luaEvent, luaPlayerContext);
                        }))
                .replaceWithVoid();
    }

    Uni<Void> handleEvent(final LuaRuntime luaRuntime,
                          final LuaEvent luaEvent,
                          final LuaPlayerContext luaPlayerContext) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> luaRuntime.handleEvent(luaEvent, luaPlayerContext));
    }
}
