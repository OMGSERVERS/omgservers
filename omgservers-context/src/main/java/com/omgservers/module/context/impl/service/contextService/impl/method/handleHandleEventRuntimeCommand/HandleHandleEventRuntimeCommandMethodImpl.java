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

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var versionId = request.getVersionId();
        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getMatchId();
        final var runtimeId = request.getRuntimeId();
        final var userId = request.getUserId();
        final var playerId = request.getPlayerId();
        final var clientId = request.getClientId();
        final var data = request.getData();
        return createLuaRuntimeContextOperation.createLuaRuntimeContext(matchmakerId, matchId, runtimeId)
                .flatMap(luaRuntimeContext -> {
                    final var luaEvent = new LuaHandleEventRuntimeCommandReceivedEvent(userId, playerId, clientId, data);
                    return handleLuaEventOperation.handleLuaEvent(versionId, luaEvent, luaRuntimeContext);
                })
                .replaceWith(new HandleHandleEventRuntimeCommandResponse(true));
    }
}
