package com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.impl.runtimeCommandHandlers;

import com.omgservers.dto.context.HandleHandleIncomingRuntimeCommandRequest;
import com.omgservers.dto.context.HandleHandleIncomingRuntimeCommandResponse;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.HandleIncomingRuntimeCommandBodyModel;
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
class HandleIncomingActorRuntimeCommandHandlerImpl implements RuntimeCommandHandler {

    final ContextModule contextModule;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.HANDLE_INCOMING;
    }

    @Override
    public Uni<Boolean> handleRuntimeCommand(RuntimeCommandModel runtimeCommand) {
        final var runtimeId = runtimeCommand.getRuntimeId();
        final var body = (HandleIncomingRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var userId = body.getUserId();
        final var playerId = body.getPlayerId();
        final var clientId = body.getClientId();
        final var incoming = body.getIncoming();
        final var request = new HandleHandleIncomingRuntimeCommandRequest(
                runtimeId, userId, playerId, clientId, incoming);
        return contextModule.getContextService()
                .handleHandleIncomingRuntimeCommand(request)
                .map(HandleHandleIncomingRuntimeCommandResponse::getResult);
    }
}
