package com.omgservers.module.context.impl.service.contextService.impl.method.handleInitRuntimeCommand;

import com.omgservers.dto.context.HandleInitRuntimeCommandRequest;
import com.omgservers.dto.context.HandleInitRuntimeCommandResponse;
import com.omgservers.module.context.impl.luaEvent.runtime.LuaInitRuntimeCommandReceivedEvent;
import com.omgservers.module.context.impl.operation.createLuaRuntimeContext.CreateLuaRuntimeContextOperation;
import com.omgservers.module.context.impl.operation.handleLuaEvent.HandleLuaEventOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleInitRuntimeCommandMethodImpl implements HandleInitRuntimeCommandMethod {

    final HandleLuaEventOperation handleLuaEventOperation;
    final CreateLuaRuntimeContextOperation createLuaRuntimeContextOperation;

    @Override
    public Uni<HandleInitRuntimeCommandResponse> handleInitRuntimeCommand(HandleInitRuntimeCommandRequest request) {
        HandleInitRuntimeCommandRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var versionId = request.getVersionId();
        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getMatchId();
        final var runtimeId = request.getRuntimeId();
        return createLuaRuntimeContextOperation.createLuaRuntimeContext(matchmakerId, matchId, runtimeId)
                .flatMap(luaRuntimeContext -> {
                    final var luaEvent = new LuaInitRuntimeCommandReceivedEvent(runtimeId);
                    return handleLuaEventOperation.handleLuaEvent(tenantId, versionId, luaEvent, luaRuntimeContext);
                })
                .replaceWith(new HandleInitRuntimeCommandResponse(true));
    }
}
