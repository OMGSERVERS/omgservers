package com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.impl.method.handleMatchCreatedEventMethod;

import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request.HandleMatchCreatedEventHelpRequest;
import io.smallrye.mutiny.Uni;

public interface HandleMatchCreatedEventMethod {
    Uni<Void> handleMatchCreatedEvent(HandleMatchCreatedEventHelpRequest request);
}
