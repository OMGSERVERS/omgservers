package com.omgservers.module.context.impl.service.handlerService.impl.method.handleMatchCreatedEvent;

import com.omgservers.dto.handler.HandleMatchCreatedEventRequest;
import com.omgservers.dto.matchmaker.GetMatchShardResponse;
import com.omgservers.dto.matchmaker.GetMatchShardedRequest;
import com.omgservers.model.match.MatchModel;
import com.omgservers.module.context.impl.operation.createLuaMatchContext.CreateLuaMatchContextOperation;
import com.omgservers.module.context.impl.operation.createLuaMatchContext.impl.LuaMatchContext;
import com.omgservers.module.context.impl.operation.createLuaRuntime.CreateLuaRuntimeOperation;
import com.omgservers.module.context.impl.operation.createLuaRuntime.impl.LuaRuntime;
import com.omgservers.module.context.impl.operation.createLuaRuntime.impl.event.LuaEvent;
import com.omgservers.module.context.impl.operation.createLuaRuntime.impl.event.LuaMatchCreatedEvent;
import com.omgservers.module.lua.LuaModule;
import com.omgservers.module.matchmaker.MatchmakerModule;
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
    final LuaModule luaModule;

    final CreateLuaRuntimeOperation createLuaRuntimeOperation;
    final CreateLuaMatchContextOperation createLuaMatchContextOperation;

    @Override
    public Uni<Void> handleMatchCreatedEvent(final HandleMatchCreatedEventRequest request) {
        HandleMatchCreatedEventRequest.validate(request);

        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getId();

        return getMatch(matchmakerId, matchId)
                .flatMap(match -> {
                    final var tenantId = match.getConfig().getTenantId();
                    final var stageId = match.getConfig().getStageId();
                    return createLuaRuntimeOperation.createLuaRuntime(tenantId, stageId)
                            .flatMap(luaRuntime -> createLuaMatchContextOperation.createLuaMatchContext(matchmakerId, matchId)
                                    .flatMap(luaMatchContext -> {
                                        final var mode = match.getConfig().getModeConfig().getName();
                                        final var luaEvent = new LuaMatchCreatedEvent(mode);
                                        return handleEvent(luaRuntime, luaEvent, luaMatchContext);
                                    }));
                });
    }

    Uni<MatchModel> getMatch(final Long matchmakerId, final Long id) {
        final var requests = new GetMatchShardedRequest(matchmakerId, id);
        return matchmakerModule.getMatchmakerShardedService().getMatch(requests)
                .map(GetMatchShardResponse::getMatch);
    }

    Uni<Void> handleEvent(final LuaRuntime luaRuntime,
                          final LuaEvent luaEvent,
                          final LuaMatchContext luaMatchContext) {
        return Uni.createFrom().voidItem()
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .invoke(voidItem -> luaRuntime.handleEvent(luaEvent, luaMatchContext));
    }
}
