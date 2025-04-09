package com.omgservers.service.server.event.impl.method;

import com.omgservers.service.server.event.dto.HandleEventRequest;
import com.omgservers.service.server.event.dto.HandleEventResponse;
import io.smallrye.mutiny.Uni;

public interface HandleEventMethod {
    Uni<HandleEventResponse> handleEvent(HandleEventRequest request);
}
