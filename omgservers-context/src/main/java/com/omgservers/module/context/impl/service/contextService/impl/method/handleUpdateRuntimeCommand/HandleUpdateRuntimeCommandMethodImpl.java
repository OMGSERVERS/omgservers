package com.omgservers.module.context.impl.service.contextService.impl.method.handleUpdateRuntimeCommand;

import com.omgservers.dto.context.HandleUpdateRuntimeCommandRequest;
import com.omgservers.dto.context.HandleUpdateRuntimeCommandResponse;
import com.omgservers.module.context.impl.luaEvent.runtime.LuaUpdateRuntimeCommandReceivedEvent;
import com.omgservers.module.context.impl.operation.handleRuntimeEvent.HandleRuntimeLuaEventOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleUpdateRuntimeCommandMethodImpl implements HandleUpdateRuntimeCommandMethod {

    final HandleRuntimeLuaEventOperation handleRuntimeLuaEventOperation;

    @Override
    public Uni<HandleUpdateRuntimeCommandResponse> handleUpdateRuntimeCommand(HandleUpdateRuntimeCommandRequest request) {
        HandleUpdateRuntimeCommandRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var versionId = request.getVersionId();
        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getMatchId();
        final var runtimeId = request.getRuntimeId();
        final var step = request.getStep();

        final var luaEvent = new LuaUpdateRuntimeCommandReceivedEvent(step);
        return handleRuntimeLuaEventOperation.handleRuntimeLuaEvent(
                        tenantId,
                        versionId,
                        matchmakerId,
                        matchId,
                        runtimeId,
                        luaEvent)
                .replaceWith(new HandleUpdateRuntimeCommandResponse(true));
    }
}
