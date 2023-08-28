package com.omgservers.module.context.impl.service.handlerService;

import com.omgservers.dto.handler.HandleMatchCreatedEventRequest;
import com.omgservers.dto.handler.HandlePlayerSignedInEventRequest;
import com.omgservers.dto.handler.HandlePlayerSignedUpEventRequest;
import io.smallrye.mutiny.Uni;

public interface ContextService {
    Uni<Void> handlePlayerSignedUpEvent(HandlePlayerSignedUpEventRequest request);

    Uni<Void> handlePlayerSignedInEvent(HandlePlayerSignedInEventRequest request);

    Uni<Void> handleMatchCreatedEvent(HandleMatchCreatedEventRequest request);
}
