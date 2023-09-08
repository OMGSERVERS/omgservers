package com.omgservers.module.context.impl.service.contextService.impl.method.handleStopRuntimeCommand;

import com.omgservers.dto.context.HandleStopRuntimeCommandRequest;
import com.omgservers.dto.context.HandleStopRuntimeCommandResponse;
import com.omgservers.module.context.impl.luaEvent.runtime.LuaStopRuntimeCommandReceivedEvent;
import com.omgservers.module.context.impl.operation.handleRuntimeEvent.HandleRuntimeLuaEventOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleStopRuntimeCommandMethodImpl implements HandleStopRuntimeCommandMethod {

    final HandleRuntimeLuaEventOperation handleRuntimeLuaEventOperation;

    @Override
    public Uni<HandleStopRuntimeCommandResponse> handleStopRuntimeCommand(HandleStopRuntimeCommandRequest request) {
        HandleStopRuntimeCommandRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var versionId = request.getVersionId();
        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getMatchId();
        final var runtimeId = request.getRuntimeId();

        final var luaEvent = new LuaStopRuntimeCommandReceivedEvent();
        return handleRuntimeLuaEventOperation.handleRuntimeLuaEvent(
                        tenantId,
                        versionId,
                        matchmakerId,
                        matchId,
                        runtimeId,
                        luaEvent)
                .replaceWith(new HandleStopRuntimeCommandResponse(true));
    }
}
