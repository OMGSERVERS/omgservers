package com.omgservers.module.system.impl.service.eventService.impl.method.getEvent;

import com.omgservers.dto.internal.GetEventRequest;
import com.omgservers.dto.internal.GetEventResponse;
import io.smallrye.mutiny.Uni;

public interface GetEventMethod {
    Uni<GetEventResponse> getEvent(GetEventRequest request);
}
