package com.omgservers.module.context.impl.service.handlerService.impl;

import com.omgservers.dto.context.HandleAddActorRuntimeCommandRequest;
import com.omgservers.dto.context.HandleAddActorRuntimeCommandResponse;
import com.omgservers.dto.context.HandleDeleteActorRuntimeCommandRequest;
import com.omgservers.dto.context.HandleDeleteActorRuntimeCommandResponse;
import com.omgservers.dto.context.HandleHandleIncomingRuntimeCommandRequest;
import com.omgservers.dto.context.HandleHandleIncomingRuntimeCommandResponse;
import com.omgservers.dto.context.HandleInitRuntimeCommandRequest;
import com.omgservers.dto.context.HandleInitRuntimeCommandResponse;
import com.omgservers.dto.context.HandleMatchCreatedEventRequest;
import com.omgservers.dto.context.HandleMatchCreatedEventResponse;
import com.omgservers.dto.context.HandlePlayerSignedInEventRequest;
import com.omgservers.dto.context.HandlePlayerSignedInEventResponse;
import com.omgservers.dto.context.HandlePlayerSignedUpEventRequest;
import com.omgservers.dto.context.HandlePlayerSignedUpEventResponse;
import com.omgservers.dto.context.HandleStopRuntimeCommandRequest;
import com.omgservers.dto.context.HandleStopRuntimeCommandResponse;
import com.omgservers.module.context.impl.service.handlerService.ContextService;
import com.omgservers.module.context.impl.service.handlerService.impl.method.handleAddActorRuntimeCommand.HandleAddActorRuntimeCommandMethod;
import com.omgservers.module.context.impl.service.handlerService.impl.method.handleDeleteActorRuntimeCommand.HandleDeleteActorRuntimeCommandMethod;
import com.omgservers.module.context.impl.service.handlerService.impl.method.handleHandleIncomingRuntimeCommand.HandleHandleIncomingRuntimeCommandMethod;
import com.omgservers.module.context.impl.service.handlerService.impl.method.handleInitRuntimeCommand.HandleInitRuntimeCommandMethod;
import com.omgservers.module.context.impl.service.handlerService.impl.method.handleMatchCreatedEvent.HandleMatchCreatedEventMethod;
import com.omgservers.module.context.impl.service.handlerService.impl.method.handlePlayerSignedInEvent.HandlePlayerSignedInEventMethod;
import com.omgservers.module.context.impl.service.handlerService.impl.method.handlePlayerSignedUpEvent.HandlePlayerSignedUpEventMethod;
import com.omgservers.module.context.impl.service.handlerService.impl.method.handleStopRuntimeCommand.HandleStopRuntimeCommandMethod;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class ContextServiceImpl implements ContextService {

    final HandlePlayerSignedUpEventMethod handlePlayerSignedUpEventHelpMethod;
    final HandlePlayerSignedInEventMethod handlePlayerSignedInEventHelpMethod;
    final HandleMatchCreatedEventMethod handleMatchCreatedEventMethod;

    final HandleHandleIncomingRuntimeCommandMethod handleHandleIncomingRuntimeCommandMethod;
    final HandleDeleteActorRuntimeCommandMethod handleDeleteActorRuntimeCommandMethod;
    final HandleAddActorRuntimeCommandMethod handleAddActorRuntimeCommandMethod;
    final HandleInitRuntimeCommandMethod handleInitRuntimeCommandMethod;
    final HandleStopRuntimeCommandMethod handleStopRuntimeCommandMethod;

    @Override
    public Uni<HandlePlayerSignedUpEventResponse> handlePlayerSignedUpEvent(final HandlePlayerSignedUpEventRequest request) {
        return handlePlayerSignedUpEventHelpMethod.handleLuaPlayerSignedUpEvent(request);
    }

    @Override
    public Uni<HandlePlayerSignedInEventResponse> handlePlayerSignedInEvent(final HandlePlayerSignedInEventRequest request) {
        return handlePlayerSignedInEventHelpMethod.handleLuaPlayerSignedInEvent(request);
    }

    @Override
    public Uni<HandleMatchCreatedEventResponse> handleMatchCreatedEvent(final HandleMatchCreatedEventRequest request) {
        return handleMatchCreatedEventMethod.handleMatchCreatedEvent(request);
    }

    @Override
    public Uni<HandleInitRuntimeCommandResponse> handleInitRuntimeCommand(HandleInitRuntimeCommandRequest request) {
        return handleInitRuntimeCommandMethod.handleInitRuntimeCommand(request);
    }

    @Override
    public Uni<HandleStopRuntimeCommandResponse> handleStopRuntimeCommand(HandleStopRuntimeCommandRequest request) {
        return handleStopRuntimeCommandMethod.handleStopRuntimeCommand(request);
    }

    @Override
    public Uni<HandleAddActorRuntimeCommandResponse> handleAddActorRuntimeCommand(HandleAddActorRuntimeCommandRequest request) {
        return handleAddActorRuntimeCommandMethod.handleAddActorRuntimeCommand(request);
    }

    @Override
    public Uni<HandleDeleteActorRuntimeCommandResponse> handleDeleteActorRuntimeCommand(HandleDeleteActorRuntimeCommandRequest request) {
        return handleDeleteActorRuntimeCommandMethod.handleDeleteActorRuntimeCommand(request);
    }

    @Override
    public Uni<HandleHandleIncomingRuntimeCommandResponse> handleHandleIncomingRuntimeCommand(HandleHandleIncomingRuntimeCommandRequest request) {
        return handleHandleIncomingRuntimeCommandMethod.handleHandleIncomingRuntimeCommand(request);
    }
}
