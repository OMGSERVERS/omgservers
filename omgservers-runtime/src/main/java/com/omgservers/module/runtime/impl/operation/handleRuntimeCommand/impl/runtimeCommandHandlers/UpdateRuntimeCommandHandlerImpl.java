package com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.impl.runtimeCommandHandlers;

import com.omgservers.dto.context.HandleUpdateRuntimeCommandRequest;
import com.omgservers.dto.context.HandleUpdateRuntimeCommandResponse;
import com.omgservers.dto.runtime.GetRuntimeShardedRequest;
import com.omgservers.dto.runtime.GetRuntimeShardedResponse;
import com.omgservers.model.runtime.RuntimeModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.UpdateRuntimeCommandBodyModel;
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
class UpdateRuntimeCommandHandlerImpl implements RuntimeCommandHandler {

    final ContextModule contextModule;
    final RuntimeModule runtimeModule;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.UPDATE_RUNTIME;
    }

    @Override
    public Uni<Boolean> handleRuntimeCommand(RuntimeCommandModel runtimeCommand) {
        final var runtimeId = runtimeCommand.getRuntimeId();
        final var runtimeCommandBody = (UpdateRuntimeCommandBodyModel) runtimeCommand.getBody();

        return getRuntime(runtimeId)
                .flatMap(runtime -> {
                    final var request = HandleUpdateRuntimeCommandRequest.builder()
                            .tenantId(runtime.getTenantId())
                            .stageId(runtime.getStageId())
                            .versionId(runtime.getVersionId())
                            .matchmakerId(runtime.getMatchmakerId())
                            .matchId(runtime.getMatchId())
                            .runtimeId(runtimeId)
                            .step(runtimeCommandBody.getStep())
                            .build();

                    return contextModule.getContextService().handleUpdateRuntimeCommand(request)
                            .map(HandleUpdateRuntimeCommandResponse::getResult);
                });
    }

    Uni<RuntimeModel> getRuntime(final Long id) {
        final var request = new GetRuntimeShardedRequest(id);
        return runtimeModule.getRuntimeShardedService().getRuntime(request)
                .map(GetRuntimeShardedResponse::getRuntime);
    }
}
