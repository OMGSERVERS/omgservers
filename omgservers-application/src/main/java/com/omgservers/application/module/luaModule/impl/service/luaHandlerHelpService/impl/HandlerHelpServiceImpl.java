package com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.impl;

import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.HandlerHelpService;
import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.impl.method.handleMatchCreatedEventMethod.HandleMatchCreatedEventMethod;
import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.impl.method.handlePlayerSignedInEventMethod.HandlePlayerSignedInEventMethod;
import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.impl.method.handlePlayerSignedUpEventMethod.HandlePlayerSignedUpEventMethod;
import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request.HandleMatchCreatedEventHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request.HandlePlayerSignedInEventHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request.HandlePlayerSignedUpEventHelpRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandlerHelpServiceImpl implements HandlerHelpService {

    final HandlePlayerSignedUpEventMethod handlePlayerSignedUpEventHelpMethod;
    final HandlePlayerSignedInEventMethod handlePlayerSignedInEventHelpMethod;
    final HandleMatchCreatedEventMethod handleMatchCreatedEventMethod;

    @Override
    public Uni<Void> handlePlayerSignedUpEvent(final HandlePlayerSignedUpEventHelpRequest request) {
        return handlePlayerSignedUpEventHelpMethod.handleLuaPlayerSignedUpEvent(request);
    }

    @Override
    public Uni<Void> handlePlayerSignedInEvent(final HandlePlayerSignedInEventHelpRequest request) {
        return handlePlayerSignedInEventHelpMethod.handleLuaPlayerSignedInEvent(request);
    }

    @Override
    public Uni<Void> handleMatchCreatedEvent(final HandleMatchCreatedEventHelpRequest request) {
        return handleMatchCreatedEventMethod.handleMatchCreatedEvent(request);
    }
}
