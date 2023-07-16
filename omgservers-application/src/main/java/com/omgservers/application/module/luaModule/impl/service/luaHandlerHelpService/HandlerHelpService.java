package com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService;

import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request.HandleMatchCreatedEventHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request.HandlePlayerSignedInEventHelpRequest;
import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request.HandlePlayerSignedUpEventHelpRequest;
import io.smallrye.mutiny.Uni;

public interface HandlerHelpService {
    Uni<Void> handlePlayerSignedUpEvent(HandlePlayerSignedUpEventHelpRequest request);

    Uni<Void> handlePlayerSignedInEvent(HandlePlayerSignedInEventHelpRequest request);

    Uni<Void> handleMatchCreatedEvent(HandleMatchCreatedEventHelpRequest request);
}
