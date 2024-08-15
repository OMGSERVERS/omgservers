package com.omgservers.service.service.event.impl.method.handleEvent;

import com.omgservers.service.service.event.dto.HandleEventRequest;
import com.omgservers.service.service.event.dto.HandleEventResponse;
import io.smallrye.mutiny.Uni;

public interface HandleEventMethod {
    Uni<HandleEventResponse> handleEvent(HandleEventRequest request);
}
