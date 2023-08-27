package com.omgservers.module.handler.impl.service.handlerService.impl;

import com.omgservers.module.handler.impl.service.handlerService.HandlerService;
import com.omgservers.module.handler.impl.service.handlerService.impl.method.handleMatchCreatedEvent.HandleMatchCreatedEventMethod;
import com.omgservers.module.handler.impl.service.handlerService.impl.method.handlePlayerSignedInEvent.HandlePlayerSignedInEventMethod;
import com.omgservers.module.handler.impl.service.handlerService.impl.method.handlePlayerSignedUpEvent.HandlePlayerSignedUpEventMethod;
import com.omgservers.dto.handler.HandleMatchCreatedEventRequest;
import com.omgservers.dto.handler.HandlePlayerSignedInEventRequest;
import com.omgservers.dto.handler.HandlePlayerSignedUpEventRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandlerServiceImpl implements HandlerService {

    final HandlePlayerSignedUpEventMethod handlePlayerSignedUpEventHelpMethod;
    final HandlePlayerSignedInEventMethod handlePlayerSignedInEventHelpMethod;
    final HandleMatchCreatedEventMethod handleMatchCreatedEventMethod;

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
}
