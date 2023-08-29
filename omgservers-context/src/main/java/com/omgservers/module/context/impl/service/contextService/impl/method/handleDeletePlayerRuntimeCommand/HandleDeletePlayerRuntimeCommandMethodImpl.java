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

        final Long tenantId = request.getTenantId();
        final Long stageId = request.getStageId();
        final Long matchmakerId = request.getMatchmakerId();
        final Long matchId = request.getMatchId();
        final Long runtimeId = request.getRuntimeId();
        final Long userId = request.getUserId();
        final Long playerId = request.getPlayerId();
        final Long clientId = request.getClientId();
        return createLuaRuntimeContextOperation.createLuaRuntimeContext(matchmakerId, matchId, runtimeId)
                .flatMap(luaRuntimeContext -> {
                    final var luaEvent = new LuaDeletePlayerRuntimeCommandReceivedEvent(userId, playerId, clientId);
                    return handleLuaEventOperation.handleLuaEvent(tenantId, stageId, luaEvent, luaRuntimeContext);
                })
                .replaceWith(new HandleDeletePlayerRuntimeCommandResponse(true));
    }
}
