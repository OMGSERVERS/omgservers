package com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.impl.runtimeCommandHandlers;

import com.omgservers.dto.context.HandleDeletePlayerRuntimeCommandRequest;
import com.omgservers.dto.context.HandleDeletePlayerRuntimeCommandResponse;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedResponse;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.DeletePlayerRuntimeCommandBodyModel;
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
class DeletePlayerRuntimeCommandHandlerImpl implements RuntimeCommandHandler {

    final ContextModule contextModule;
    final RuntimeModule runtimeModule;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.DELETE_PLAYER;
    }

    @Override
    public Uni<Boolean> handleRuntimeCommand(RuntimeCommandModel runtimeCommand) {
        final var runtimeId = runtimeCommand.getRuntimeId();
        final var runtimeCommandBody = (DeletePlayerRuntimeCommandBodyModel) runtimeCommand.getBody();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    final var handleDeleteActorRuntimeCommandRequest = HandleDeletePlayerRuntimeCommandRequest.builder()
                            .tenantId(runtime.getTenantId())
                            .stageId(runtime.getStageId())
                            .versionId(runtime.getVersionId())
                            .matchmakerId(runtime.getMatchmakerId())
                            .matchId(runtime.getMatchId())
                            .runtimeId(runtimeId)
                            .userId(runtimeCommandBody.getUserId())
                            .playerId(runtimeCommandBody.getPlayerId())
                            .clientId(runtimeCommandBody.getClientId())
                            .build();

                    return contextModule.getContextService().handleDeletePlayerRuntimeCommand(handleDeleteActorRuntimeCommandRequest)
                            .map(HandleDeletePlayerRuntimeCommandResponse::getResult);
                });
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeShardedRequest(id);
        return runtimeModule.getRuntimeShardedService().getRuntime(request)
                .map(GetRuntimeShardedResponse::getRuntime);
    }
}
