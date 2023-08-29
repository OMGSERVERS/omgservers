package com.omgservers.module.context.impl.service.contextService.impl.method.handlePlayerSignedInEvent;

import com.omgservers.dto.context.HandlePlayerSignedInEventRequest;
import com.omgservers.dto.context.HandlePlayerSignedInEventResponse;
import io.smallrye.mutiny.Uni;

public interface HandlePlayerSignedInEventMethod {
    Uni<HandlePlayerSignedInEventResponse> handleLuaPlayerSignedInEvent(HandlePlayerSignedInEventRequest request);
}
