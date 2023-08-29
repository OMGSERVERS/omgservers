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
                    final var luaEvent = new LuaAddPlayerRuntimeCommandReceivedEvent(userId, playerId, clientId);
                    return handleLuaEventOperation.handleLuaEvent(tenantId, stageId, luaEvent, luaRuntimeContext);
                })
                .replaceWith(new HandleAddPlayerRuntimeCommandResponse(true));
    }
}
