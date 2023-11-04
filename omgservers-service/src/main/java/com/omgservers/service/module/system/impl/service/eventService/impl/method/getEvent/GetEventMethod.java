package com.omgservers.service.module.system.impl.service.eventService.impl.method.getEvent;

import com.omgservers.model.dto.system.GetEventRequest;
import com.omgservers.model.dto.system.GetEventResponse;
import io.smallrye.mutiny.Uni;

public interface GetEventMethod {
    Uni<GetEventResponse> getEvent(GetEventRequest request);
}
