package com.omgservers.module.system.impl.service.eventService.impl.method.getEvent;

import com.omgservers.model.dto.internal.GetEventRequest;
import com.omgservers.model.dto.internal.GetEventResponse;
import io.smallrye.mutiny.Uni;

public interface GetEventMethod {
    Uni<GetEventResponse> getEvent(GetEventRequest request);
}
