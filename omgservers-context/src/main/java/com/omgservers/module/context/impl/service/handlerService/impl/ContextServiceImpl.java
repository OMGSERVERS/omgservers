package com.omgservers.module.context.impl.service.handlerService.impl;

import com.omgservers.dto.handler.HandleAddActorRuntimeCommandRequest;
import com.omgservers.dto.handler.HandleDeleteActorRuntimeCommandRequest;
import com.omgservers.dto.handler.HandleHandleIncomingRuntimeCommandRequest;
import com.omgservers.dto.handler.HandleInitRuntimeCommandRequest;
import com.omgservers.dto.handler.HandleMatchCreatedEventRequest;
import com.omgservers.dto.handler.HandlePlayerSignedInEventRequest;
import com.omgservers.dto.handler.HandlePlayerSignedUpEventRequest;
import com.omgservers.dto.handler.HandleStopRuntimeCommandRequest;
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
    public Uni<Void> handlePlayerSignedUpEvent(final HandlePlayerSignedUpEventRequest request) {
        return handlePlayerSignedUpEventHelpMethod.handleLuaPlayerSignedUpEvent(request);
    }

    @Override
    public Uni<Void> handlePlayerSignedInEvent(final HandlePlayerSignedInEventRequest request) {
        return handlePlayerSignedInEventHelpMethod.handleLuaPlayerSignedInEvent(request);
    }

    @Override
    public Uni<Void> handleMatchCreatedEvent(final HandleMatchCreatedEventRequest request) {
        return handleMatchCreatedEventMethod.handleMatchCreatedEvent(request);
    }

    @Override
    public Uni<Void> handleInitRuntimeCommand(HandleInitRuntimeCommandRequest request) {
        return handleInitRuntimeCommandMethod.handleInitRuntimeCommand(request);
    }

    @Override
    public Uni<Void> handleStopRuntimeCommand(HandleStopRuntimeCommandRequest request) {
        return handleStopRuntimeCommandMethod.handleStopRuntimeCommand(request);
    }

    @Override
    public Uni<Void> handleAddActorRuntimeCommand(HandleAddActorRuntimeCommandRequest request) {
        return handleAddActorRuntimeCommandMethod.handleAddActorRuntimeCommand(request);
    }

    @Override
    public Uni<Void> handleDeleteActorRuntimeCommand(HandleDeleteActorRuntimeCommandRequest request) {
        return handleDeleteActorRuntimeCommandMethod.handleDeleteActorRuntimeCommand(request);
    }

    @Override
    public Uni<Void> handleHandleIncomingRuntimeCommand(HandleHandleIncomingRuntimeCommandRequest request) {
        return handleHandleIncomingRuntimeCommandMethod.handleHandleIncomingRuntimeCommand(request);
    }
}
