package com.omgservers.module.context.impl.service.handlerService.impl.method.handlePlayerSignedUpEvent;

import com.omgservers.dto.handler.HandlePlayerSignedUpEventRequest;
import io.smallrye.mutiny.Uni;

public interface HandlePlayerSignedUpEventMethod {
    Uni<Void> handleLuaPlayerSignedUpEvent(HandlePlayerSignedUpEventRequest request);
}
