package com.omgservers.module.context.impl.service.handlerService.impl.method.handleMatchCreatedEvent;

import com.omgservers.dto.context.HandleMatchCreatedEventRequest;
import com.omgservers.dto.context.HandleMatchCreatedEventResponse;
import io.smallrye.mutiny.Uni;

public interface HandleMatchCreatedEventMethod {
    Uni<HandleMatchCreatedEventResponse> handleMatchCreatedEvent(HandleMatchCreatedEventRequest request);
}
