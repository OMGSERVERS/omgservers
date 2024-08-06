package com.omgservers.service.server.service.event.impl.method.handleEvent;

import com.omgservers.schema.service.system.HandleEventRequest;
import com.omgservers.schema.service.system.HandleEventResponse;
import io.smallrye.mutiny.Uni;

public interface HandleEventMethod {
    Uni<HandleEventResponse> handleEvent(HandleEventRequest request);
}
