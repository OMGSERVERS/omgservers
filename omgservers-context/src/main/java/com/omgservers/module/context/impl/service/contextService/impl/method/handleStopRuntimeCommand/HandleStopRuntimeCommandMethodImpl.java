package com.omgservers.module.context.impl.service.contextService.impl.method.handleStopRuntimeCommand;

import com.omgservers.dto.context.HandleStopRuntimeCommandRequest;
import com.omgservers.dto.context.HandleStopRuntimeCommandResponse;
import com.omgservers.module.context.impl.luaEvent.runtime.LuaStopRuntimeCommandReceivedEvent;
import com.omgservers.module.context.impl.operation.createLuaRuntimeContext.CreateLuaRuntimeContextOperation;
import com.omgservers.module.context.impl.operation.handleLuaEvent.HandleLuaEventOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleStopRuntimeCommandMethodImpl implements HandleStopRuntimeCommandMethod {

    final HandleLuaEventOperation handleLuaEventOperation;
    final CreateLuaRuntimeContextOperation createLuaRuntimeContextOperation;

    @Override
    public Uni<HandleStopRuntimeCommandResponse> handleStopRuntimeCommand(HandleStopRuntimeCommandRequest request) {
        HandleStopRuntimeCommandRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var versionId = request.getVersionId();
        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getMatchId();
        final var runtimeId = request.getRuntimeId();
        return createLuaRuntimeContextOperation.createLuaRuntimeContext(matchmakerId, matchId, runtimeId)
                .flatMap(luaRuntimeContext -> {
                    final var luaEvent = new LuaStopRuntimeCommandReceivedEvent();
                    return handleLuaEventOperation.handleLuaEvent(versionId, luaEvent, luaRuntimeContext);
                })
                .replaceWith(new HandleStopRuntimeCommandResponse(true));
    }
}
