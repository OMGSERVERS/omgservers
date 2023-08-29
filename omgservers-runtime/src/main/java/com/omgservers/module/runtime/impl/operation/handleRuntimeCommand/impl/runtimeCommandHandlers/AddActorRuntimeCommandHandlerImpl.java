package com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.impl.runtimeCommandHandlers;

import com.omgservers.dto.context.HandleAddActorRuntimeCommandRequest;
import com.omgservers.dto.context.HandleAddActorRuntimeCommandResponse;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.AddActorRuntimeCommandBodyModel;
import com.omgservers.module.context.ContextModule;
import com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.impl.RuntimeCommandHandler;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class AddActorRuntimeCommandHandlerImpl implements RuntimeCommandHandler {

    final ContextModule contextModule;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.ADD_ACTOR;
    }

    @Override
    public Uni<Boolean> handleRuntimeCommand(RuntimeCommandModel runtimeCommand) {
        final var runtimeId = runtimeCommand.getRuntimeId();
        final var body = (AddActorRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var userId = body.getUserId();
        final var playerId = body.getPlayerId();
        final var clientId = body.getClientId();
        final var handleAddActorRuntimeCommandRequest =
                new HandleAddActorRuntimeCommandRequest(runtimeId, userId, playerId, clientId);
        return contextModule.getContextService()
                .handleAddActorRuntimeCommand(handleAddActorRuntimeCommandRequest)
                .map(HandleAddActorRuntimeCommandResponse::getResult);
    }
}
