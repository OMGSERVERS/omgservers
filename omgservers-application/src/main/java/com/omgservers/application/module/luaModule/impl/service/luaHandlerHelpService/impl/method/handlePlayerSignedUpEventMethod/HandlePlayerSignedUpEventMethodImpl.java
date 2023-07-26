package com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.impl.method.handlePlayerSignedUpEventMethod;

import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.LuaRuntime;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.RuntimeHelpService;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.event.LuaEvent;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.event.LuaPlayerSignedUpEvent;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.player.LuaPlayerContext;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreateLuaRuntimeHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreatePlayerContextHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreateLuaRuntimeHelpResponse;
import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request.HandlePlayerSignedUpEventHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreatePlayerContextHelpResponse;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandlePlayerSignedUpEventMethodImpl implements HandlePlayerSignedUpEventMethod {

    final RuntimeHelpService runtimeHelpService;

    @Override
    public Uni<Void> handleLuaPlayerSignedUpEvent(final HandlePlayerSignedUpEventHelpRequest request) {
        HandlePlayerSignedUpEventHelpRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var userId = request.getUserId();
        final var playerId = request.getPlayerId();
        final var clientId = request.getClientId();

        return createLuaRuntime(tenantId, stageId)
                .flatMap(luaRuntime -> createPlayerContext(userId, playerId, clientId)
                        .flatMap(luaPlayerContext -> {
                            final var luaEvent = new LuaPlayerSignedUpEvent(userId, playerId, clientId);
                            return handleEvent(luaRuntime, luaEvent, luaPlayerContext);
                        }))
                .replaceWithVoid();
    }

    Uni<LuaRuntime> createLuaRuntime(final Long tenantId, final Long stageId) {
        final var request = new CreateLuaRuntimeHelpRequest(tenantId, stageId);
        return runtimeHelpService.createLuaRuntime(request)
                .map(CreateLuaRuntimeHelpResponse::getLuaRuntime);
    }

    Uni<LuaPlayerContext> createPlayerContext(final Long userId,
                                              final Long playerId,
                                              final Long clientId) {
        final var request = new CreatePlayerContextHelpRequest(userId, playerId, clientId);
        return runtimeHelpService.createPlayerContext(request)
                .map(CreatePlayerContextHelpResponse::getLuaPlayerContext);
    }

    Uni<Void> handleEvent(final LuaRuntime luaRuntime,
                          final LuaEvent luaEvent,
                          final LuaPlayerContext luaPlayerContext) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> luaRuntime.handleEvent(luaEvent, luaPlayerContext));
    }
}
