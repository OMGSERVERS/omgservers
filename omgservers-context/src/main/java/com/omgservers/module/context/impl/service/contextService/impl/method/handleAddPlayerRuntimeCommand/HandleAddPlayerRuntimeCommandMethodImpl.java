package com.omgservers.module.context.impl.service.contextService.impl.method.handleAddPlayerRuntimeCommand;

import com.omgservers.dto.context.HandleAddPlayerRuntimeCommandRequest;
import com.omgservers.dto.context.HandleAddPlayerRuntimeCommandResponse;
import com.omgservers.module.context.impl.luaEvent.runtime.LuaAddPlayerRuntimeCommandReceivedEvent;
import com.omgservers.module.context.impl.operation.handleRuntimeEvent.HandleRuntimeLuaEventOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleAddPlayerRuntimeCommandMethodImpl implements HandleAddPlayerRuntimeCommandMethod {

    final HandleRuntimeLuaEventOperation handleRuntimeLuaEventOperation;

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

        final var luaEvent = new LuaAddPlayerRuntimeCommandReceivedEvent(userId, playerId, clientId);

        return handleRuntimeLuaEventOperation.handleRuntimeLuaEvent(
                        tenantId,
                        versionId,
                        matchmakerId,
                        matchId,
                        runtimeId,
                        luaEvent)
                .replaceWith(new HandleAddPlayerRuntimeCommandResponse(true));
    }
}
