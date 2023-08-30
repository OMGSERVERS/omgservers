package com.omgservers.module.context.impl.service.contextService.impl.method.handleDeletePlayerRuntimeCommand;

import com.omgservers.dto.context.HandleDeletePlayerRuntimeCommandRequest;
import com.omgservers.dto.context.HandleDeletePlayerRuntimeCommandResponse;
import com.omgservers.module.context.impl.luaEvent.runtime.LuaDeletePlayerRuntimeCommandReceivedEvent;
import com.omgservers.module.context.impl.operation.createLuaRuntimeContext.CreateLuaRuntimeContextOperation;
import com.omgservers.module.context.impl.operation.handleLuaEvent.HandleLuaEventOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleDeletePlayerRuntimeCommandMethodImpl implements HandleDeletePlayerRuntimeCommandMethod {

    final HandleLuaEventOperation handleLuaEventOperation;
    final CreateLuaRuntimeContextOperation createLuaRuntimeContextOperation;

    @Override
    public Uni<HandleDeletePlayerRuntimeCommandResponse> handleDeletePlayerRuntimeCommand(HandleDeletePlayerRuntimeCommandRequest request) {
        HandleDeletePlayerRuntimeCommandRequest.validate(request);

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
                    final var luaEvent = new LuaDeletePlayerRuntimeCommandReceivedEvent(userId, playerId, clientId);
                    return handleLuaEventOperation.handleLuaEvent(versionId, luaEvent, luaRuntimeContext);
                })
                .replaceWith(new HandleDeletePlayerRuntimeCommandResponse(true));
    }
}
