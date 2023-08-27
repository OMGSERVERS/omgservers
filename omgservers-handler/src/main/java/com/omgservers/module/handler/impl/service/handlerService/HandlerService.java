package com.omgservers.module.handler.impl.service.handlerService;

import com.omgservers.dto.handler.HandleMatchCreatedEventRequest;
import com.omgservers.dto.handler.HandlePlayerSignedInEventRequest;
import com.omgservers.dto.handler.HandlePlayerSignedUpEventRequest;
import io.smallrye.mutiny.Uni;

public interface HandlerService {
    Uni<Void> handlePlayerSignedUpEvent(HandlePlayerSignedUpEventRequest request);

    Uni<Void> handlePlayerSignedInEvent(HandlePlayerSignedInEventRequest request);

    Uni<Void> handleMatchCreatedEvent(HandleMatchCreatedEventRequest request);
}
