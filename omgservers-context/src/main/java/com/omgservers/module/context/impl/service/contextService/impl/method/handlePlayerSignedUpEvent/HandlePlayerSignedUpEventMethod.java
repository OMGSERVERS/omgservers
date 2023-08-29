package com.omgservers.module.context.impl.service.contextService.impl.method.handlePlayerSignedUpEvent;

import com.omgservers.dto.context.HandlePlayerSignedUpEventRequest;
import com.omgservers.dto.context.HandlePlayerSignedUpEventResponse;
import io.smallrye.mutiny.Uni;

public interface HandlePlayerSignedUpEventMethod {
    Uni<HandlePlayerSignedUpEventResponse> handleLuaPlayerSignedUpEvent(HandlePlayerSignedUpEventRequest request);
}
