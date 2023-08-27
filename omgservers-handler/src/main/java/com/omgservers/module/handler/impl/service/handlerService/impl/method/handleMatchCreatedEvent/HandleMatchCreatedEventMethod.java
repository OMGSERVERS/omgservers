package com.omgservers.module.handler.impl.service.handlerService.impl.method.handleMatchCreatedEvent;

import com.omgservers.dto.handler.HandleMatchCreatedEventRequest;
import io.smallrye.mutiny.Uni;

public interface HandleMatchCreatedEventMethod {
    Uni<Void> handleMatchCreatedEvent(HandleMatchCreatedEventRequest request);
}
