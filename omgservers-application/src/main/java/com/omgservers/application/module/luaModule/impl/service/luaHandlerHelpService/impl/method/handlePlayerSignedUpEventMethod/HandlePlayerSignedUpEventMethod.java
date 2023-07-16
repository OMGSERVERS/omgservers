package com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.impl.method.handlePlayerSignedUpEventMethod;

import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request.HandlePlayerSignedUpEventHelpRequest;
import io.smallrye.mutiny.Uni;

public interface HandlePlayerSignedUpEventMethod {
    Uni<Void> handleLuaPlayerSignedUpEvent(HandlePlayerSignedUpEventHelpRequest request);
}
