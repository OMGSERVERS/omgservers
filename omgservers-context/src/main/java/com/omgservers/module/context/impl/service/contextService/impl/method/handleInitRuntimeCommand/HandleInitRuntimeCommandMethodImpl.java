package com.omgservers.module.context.impl.service.contextService.impl.method.handleInitRuntimeCommand;

import com.omgservers.dto.context.HandleInitRuntimeCommandRequest;
import com.omgservers.dto.context.HandleInitRuntimeCommandResponse;
import com.omgservers.module.context.impl.luaEvent.runtime.LuaInitRuntimeCommandReceivedEvent;
import com.omgservers.module.context.impl.operation.handleRuntimeEvent.HandleRuntimeLuaEventOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandleInitRuntimeCommandMethodImpl implements HandleInitRuntimeCommandMethod {

    final HandleRuntimeLuaEventOperation handleRuntimeLuaEventOperation;

    @Override
    public Uni<HandleInitRuntimeCommandResponse> handleInitRuntimeCommand(HandleInitRuntimeCommandRequest request) {
        HandleInitRuntimeCommandRequest.validate(request);

        final var tenantId = request.getTenantId();
        final var stageId = request.getStageId();
        final var versionId = request.getVersionId();
        final var matchmakerId = request.getMatchmakerId();
        final var matchId = request.getMatchId();
        final var runtimeId = request.getRuntimeId();

        final var luaEvent = new LuaInitRuntimeCommandReceivedEvent(runtimeId);
        return handleRuntimeLuaEventOperation.handleRuntimeLuaEvent(
                        tenantId,
                        versionId,
                        matchmakerId,
                        matchId,
                        runtimeId,
                        luaEvent)
                .replaceWith(new HandleInitRuntimeCommandResponse(true));
    }
}
