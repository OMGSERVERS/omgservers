package com.omgservers.module.context.impl.service.contextService.impl.method.handleHandleEventRuntimeCommand;

import com.omgservers.dto.context.HandleHandleEventRuntimeCommandRequest;
import com.omgservers.dto.context.HandleHandleEventRuntimeCommandResponse;
import com.omgservers.module.context.impl.luaEvent.runtime.LuaHandleEventRuntimeCommandReceivedEvent;
import com.omgservers.module.context.impl.operation.createLuaRuntimeContext.CreateLuaRuntimeContextOperation;
import com.omgservers.module.context.impl.operation.handleLuaEvent.HandleLuaEventOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleHandleEventRuntimeCommandMethodImpl implements HandleHandleEventRuntimeCommandMethod {

    final HandleLuaEventOperation handleLuaEventOperation;
    final CreateLuaRuntimeContextOperation createLuaRuntimeContextOperation;

    @Override
    public Uni<HandleHandleEventRuntimeCommandResponse> handleHandleEventRuntimeCommand(HandleHandleEventRuntimeCommandRequest request) {
        HandleHandleEventRuntimeCommandRequest.validate(request);

        final Long tenantId = request.getTenantId();
        final Long stageId = request.getStageId();
        final Long matchmakerId = request.getMatchmakerId();
        final Long matchId = request.getMatchId();
        final Long runtimeId = request.getRuntimeId();
        final Long userId = request.getUserId();
        final Long playerId = request.getPlayerId();
        final Long clientId = request.getClientId();
        final String data = request.getData();
        return createLuaRuntimeContextOperation.createLuaRuntimeContext(matchmakerId, matchId, runtimeId)
                .flatMap(luaRuntimeContext -> {
                    final var luaEvent = new LuaHandleEventRuntimeCommandReceivedEvent(userId, playerId, clientId, data);
                    return handleLuaEventOperation.handleLuaEvent(tenantId, stageId, luaEvent, luaRuntimeContext);
                })
                .replaceWith(new HandleHandleEventRuntimeCommandResponse(true));
    }
}
