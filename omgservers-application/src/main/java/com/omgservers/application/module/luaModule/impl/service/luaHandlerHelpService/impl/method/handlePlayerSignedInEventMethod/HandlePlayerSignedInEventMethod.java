package com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.impl.method.handlePlayerSignedInEventMethod;

import com.omgservers.application.module.luaModule.impl.service.luaHandlerHelpService.request.HandlePlayerSignedInEventHelpRequest;
import io.smallrye.mutiny.Uni;

public interface HandlePlayerSignedInEventMethod {
    Uni<Void> handleLuaPlayerSignedInEvent(HandlePlayerSignedInEventHelpRequest request);
}
