package com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.impl.runtimeCommandHandlers;

import com.omgservers.dto.context.HandleDeleteActorRuntimeCommandRequest;
import com.omgservers.dto.context.HandleDeleteActorRuntimeCommandResponse;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.DeleteActorRuntimeCommandBodyModel;
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
class DeleteActorRuntimeCommandHandlerImpl implements RuntimeCommandHandler {

    final ContextModule contextModule;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.DELETE_ACTOR;
    }

    @Override
    public Uni<Boolean> handleRuntimeCommand(RuntimeCommandModel runtimeCommand) {
        final var runtimeId = runtimeCommand.getRuntimeId();
        final var body = (DeleteActorRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var userId = body.getUserId();
        final var playerId = body.getPlayerId();
        final var clientId = body.getClientId();
        final var handleDeleteActorRuntimeCommandRequest =
                new HandleDeleteActorRuntimeCommandRequest(runtimeId, userId, playerId, clientId);
        return contextModule.getContextService()
                .handleDeleteActorRuntimeCommand(handleDeleteActorRuntimeCommandRequest)
                .map(HandleDeleteActorRuntimeCommandResponse::getResult);
    }
}
