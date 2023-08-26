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
import com.omgservers.dto.matchmakerModule.GetMatchRoutedRequest;
import com.omgservers.dto.matchmakerModule.GetMatchInternalResponse;
import com.omgservers.model.match.MatchModel;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleMatchCreatedEventMethodImpl implements HandleMatchCreatedEventMethod {

    final MatchmakerModule matchmakerModule;
    final RuntimeHelpService runtimeHelpService;

    @Override
    public Uni<Void> handleMatchCreatedEvent(final HandleMatchCreatedEventHelpRequest request) {
        HandleMatchCreatedEventHelpRequest.validate(request);

        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getId();

        return getMatch(matchmakerId, matchId)
                .flatMap(match -> {
                    final var tenantId = match.getConfig().getTenantId();
                    final var stageId = match.getConfig().getStageId();
                    return createLuaRuntime(tenantId, stageId)
                            .flatMap(luaRuntime -> createMatchContext(matchmakerId, matchId)
                                    .flatMap(luaMatchContext -> {
                                        final var mode = match.getConfig().getModeConfig().getName();
                                        final var luaEvent = new LuaMatchCreatedEvent(mode);
                                        return handleEvent(luaRuntime, luaEvent, luaMatchContext);
                                    }));
                });
    }

    Uni<MatchModel> getMatch(final Long matchmakerId, final Long id) {
        final var requests = new GetMatchRoutedRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerInternalService().getMatch(requests)
                .map(GetMatchInternalResponse::getMatch);
    }

    Uni<LuaRuntime> createLuaRuntime(final Long tenantId, final Long stageId) {
        final var request = new CreateLuaRuntimeHelpRequest(tenantId, stageId);
        return runtimeHelpService.createLuaRuntime(request)
                .map(CreateLuaRuntimeHelpResponse::getLuaRuntime);
    }

    Uni<LuaMatchContext> createMatchContext(final Long matchmakerId, final Long id) {
        final var request = new CreateMatchContextHelpRequest(matchmakerId, id);
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
