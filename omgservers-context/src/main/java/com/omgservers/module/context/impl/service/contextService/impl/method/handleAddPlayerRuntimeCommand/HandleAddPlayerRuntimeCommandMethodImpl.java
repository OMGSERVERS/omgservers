package com.omgservers.module.context.impl.service.contextService.impl.method.handleAddPlayerRuntimeCommand;

import com.omgservers.dto.context.HandleAddPlayerRuntimeCommandRequest;
import com.omgservers.dto.context.HandleAddPlayerRuntimeCommandResponse;
import com.omgservers.module.context.impl.luaEvent.runtime.LuaAddPlayerRuntimeCommandReceivedEvent;
import com.omgservers.module.context.impl.operation.createLuaRuntimeContext.CreateLuaRuntimeContextOperation;
import com.omgservers.module.context.impl.operation.handleLuaEvent.HandleLuaEventOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleAddPlayerRuntimeCommandMethodImpl implements HandleAddPlayerRuntimeCommandMethod {

    final HandleLuaEventOperation handleLuaEventOperation;
    final CreateLuaRuntimeContextOperation createLuaRuntimeContextOperation;

    @Override
    public Uni<HandleAddPlayerRuntimeCommandResponse> handleAddPlayerRuntimeCommand(final HandleAddPlayerRuntimeCommandRequest request) {
        HandleAddPlayerRuntimeCommandRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var versionId = request.getVersionId();
        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getMatchId();
        final var runtimeId = request.getRuntimeId();
        final var userId = request.getUserId();
        final var playerId = request.getPlayerId();
        final var clientId = request.getClientId();
        return createLuaRuntimeContextOperation.createLuaRuntimeContext(matchmakerId, matchId, runtimeId)
                .flatMap(luaRuntimeContext -> {
                    final var luaEvent = new LuaAddPlayerRuntimeCommandReceivedEvent(userId, playerId, clientId);
                    return handleLuaEventOperation.handleLuaEvent(versionId, luaEvent, luaRuntimeContext);
                })
                .replaceWith(new HandleAddPlayerRuntimeCommandResponse(true));
    }
}