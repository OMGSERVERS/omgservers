package com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.impl.runtimeCommandHandlers;

import com.omgservers.dto.context.HandleHandleEventRuntimeCommandRequest;
import com.omgservers.dto.context.HandleHandleEventRuntimeCommandResponse;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedResponse;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.HandleEventRuntimeCommandBodyModel;
import com.omgservers.module.context.ContextModule;
import com.omgservers.module.runtime.RuntimeModule;
import com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.impl.RuntimeCommandHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class HandleEventRuntimeCommandHandlerImpl implements RuntimeCommandHandler {

    final ContextModule contextModule;
    final RuntimeModule runtimeModule;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.HANDLE_EVENT;
    }

    @Override
    public Uni<Boolean> handleRuntimeCommand(RuntimeCommandModel runtimeCommand) {
        final var runtimeId = runtimeCommand.getRuntimeId();
        final var runtimeCommandBody = (HandleEventRuntimeCommandBodyModel) runtimeCommand.getBody();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    final var request = HandleHandleEventRuntimeCommandRequest.builder()
                            .tenantId(runtime.getTenantId())
                            .stageId(runtime.getStageId())
                            .matchmakerId(runtime.getMatchmakerId())
                            .matchId(runtime.getMatchId())
                            .runtimeId(runtimeId)
                            .userId(runtimeCommandBody.getUserId())
                            .playerId(runtimeCommandBody.getPlayerId())
                            .clientId(runtimeCommandBody.getClientId())
                            .data(runtimeCommandBody.getData())
                            .build();

                    return contextModule.getContextService().handleHandleEventRuntimeCommand(request)
                            .map(HandleHandleEventRuntimeCommandResponse::getResult);
                });
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeShardedRequest(id);
        return runtimeModule.getRuntimeShardedService().getRuntime(request)
                .map(GetRuntimeShardedResponse::getRuntime);
    }
}
