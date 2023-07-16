package com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.impl.method.handleMatchCreatedEventMethod;

import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request.HandleMatchCreatedEventHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.RuntimeHelpService;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.LuaRuntime;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.event.LuaEvent;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.event.LuaMatchCreatedEvent;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.impl.runtime.match.LuaMatchContext;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreateLuaRuntimeHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.request.CreateMatchContextHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreateLuaRuntimeHelpResponse;
import com.omgservers.application.module.luaModule.impl.service.luaRuntimeHelpService.response.CreateMatchContextHelpResponse;
import com.omgservers.application.module.matchmakerModule.MatchmakerModule;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.request.GetMatchInternalRequest;
import com.omgservers.application.module.matchmakerModule.impl.service.matchmakerInternalService.response.GetMatchInternalResponse;
import com.omgservers.application.module.matchmakerModule.model.match.MatchModel;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleMatchCreatedEventMethodImpl implements HandleMatchCreatedEventMethod {

    final MatchmakerModule matchmakerModule;
    final RuntimeHelpService runtimeHelpService;

    @Override
    public Uni<Void> handleMatchCreatedEvent(final HandleMatchCreatedEventHelpRequest request) {
        HandleMatchCreatedEventHelpRequest.validate(request);

        final var matchmaker = request.getMatchmaker();
        final var matchUuid = request.getUuid();

        return getMatch(matchmaker, matchUuid)
                .flatMap(match -> {
                    final var tenant = match.getConfig().getTenant();
                    final var stage = match.getConfig().getStage();
                    return createLuaRuntime(tenant, stage)
                            .flatMap(luaRuntime -> createMatchContext(matchmaker, matchUuid)
                                    .flatMap(luaMatchContext -> {
                                        final var mode = match.getConfig().getModeConfig().getName();
                                        final var luaEvent = new LuaMatchCreatedEvent(mode);
                                        return handleEvent(luaRuntime, luaEvent, luaMatchContext);
                                    }));
                });
    }

    Uni<MatchModel> getMatch(final UUID matchmaker, final UUID uuid) {
        final var requests = new GetMatchInternalRequest(matchmaker, uuid);
        return matchmakerModule.getMatchmakerInternalService().getMatch(requests)
                .map(GetMatchInternalResponse::getMatch);
    }

    Uni<LuaRuntime> createLuaRuntime(final UUID tenant, final UUID stage) {
        final var request = new CreateLuaRuntimeHelpRequest(tenant, stage);
        return runtimeHelpService.createLuaRuntime(request)
                .map(CreateLuaRuntimeHelpResponse::getLuaRuntime);
    }

    Uni<LuaMatchContext> createMatchContext(final UUID matchmaker, final UUID uuid) {
        final var request = new CreateMatchContextHelpRequest(matchmaker, uuid);
        return runtimeHelpService.createMatchContext(request)
                .map(CreateMatchContextHelpResponse::getLuaMatchContext);
    }

    Uni<Void> handleEvent(final LuaRuntime luaRuntime,
                          final LuaEvent luaEvent,
                          final LuaMatchContext luaMatchContext) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> luaRuntime.handleEvent(luaEvent, luaMatchContext));
    }
}
