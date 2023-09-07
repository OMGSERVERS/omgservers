package com.omgservers.module.system.impl.service.eventService.impl.method.fireEvent;

import com.omgservers.dto.internal.FireEventRequest;
import com.omgservers.dto.internal.FireEventResponse;
import io.smallrye.mutiny.Uni;

public interface FireEventMethod {
    Uni<FireEventResponse> fireEvent(FireEventRequest request);
}
