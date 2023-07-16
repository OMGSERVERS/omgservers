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

        final var tenant = request.getTenant();
        final var stage = request.getStage();
        final var user = request.getUser();
        final var player = request.getPlayer();
        final var client = request.getClient();

        return createLuaRuntime(tenant, stage)
                .flatMap(luaRuntime -> createPlayerContext(user, player, client)
                        .flatMap(luaPlayerContext -> {
                            final var luaEvent = new LuaPlayerSignedUpEvent(user, player, client);
                            return handleEvent(luaRuntime, luaEvent, luaPlayerContext);
                        }))
                .replaceWithVoid();
    }

    Uni<LuaRuntime> createLuaRuntime(final UUID tenant, final UUID stage) {
        final var request = new CreateLuaRuntimeHelpRequest(tenant, stage);
        return runtimeHelpService.createLuaRuntime(request)
                .map(CreateLuaRuntimeHelpResponse::getLuaRuntime);
    }

    Uni<LuaPlayerContext> createPlayerContext(final UUID user,
                                              final UUID player,
                                              final UUID client) {
        final var request = new CreatePlayerContextHelpRequest(user, player, client);
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
