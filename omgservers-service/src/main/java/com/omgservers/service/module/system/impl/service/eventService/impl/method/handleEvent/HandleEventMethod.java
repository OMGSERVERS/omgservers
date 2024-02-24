package com.omgservers.service.module.system.impl.service.eventService.impl.method.handleEvent;

import com.omgservers.model.dto.system.HandleEventRequest;
import com.omgservers.model.dto.system.HandleEventResponse;
import io.smallrye.mutiny.Uni;

public interface HandleEventMethod {
    Uni<HandleEventResponse> handleEvent(HandleEventRequest request);
}
