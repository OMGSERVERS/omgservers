package com.omgservers.module.context.impl.service.contextService.impl.method.handleUpdateRuntimeCommand;

import com.omgservers.dto.context.HandleUpdateRuntimeCommandRequest;
import com.omgservers.dto.context.HandleUpdateRuntimeCommandResponse;
import com.omgservers.module.context.impl.luaEvent.runtime.LuaUpdateRuntimeCommandReceivedEvent;
import com.omgservers.module.context.impl.operation.createLuaRuntimeContext.CreateLuaRuntimeContextOperation;
import com.omgservers.module.context.impl.operation.handleLuaEvent.HandleLuaEventOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleUpdateRuntimeCommandMethodImpl implements HandleUpdateRuntimeCommandMethod {

    final HandleLuaEventOperation handleLuaEventOperation;
    final CreateLuaRuntimeContextOperation createLuaRuntimeContextOperation;

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
        return createLuaRuntimeContextOperation.createLuaRuntimeContext(matchmakerId, matchId, runtimeId)
                .flatMap(luaRuntimeContext -> {
                    final var luaEvent = new LuaUpdateRuntimeCommandReceivedEvent(step);
                    return handleLuaEventOperation.handleLuaEvent(versionId, luaEvent, luaRuntimeContext);
                })
                .replaceWith(new HandleUpdateRuntimeCommandResponse(true));
    }
}