package com.omgservers.module.handler.impl.service.handlerService.impl.method.handlePlayerSignedInEvent;

import com.omgservers.dto.handler.HandlePlayerSignedInEventRequest;
import io.smallrye.mutiny.Uni;

public interface HandlePlayerSignedInEventMethod {
    Uni<Void> handleLuaPlayerSignedInEvent(HandlePlayerSignedInEventRequest request);
}
