package com.omgservers.module.system.impl.service.eventService;

import com.omgservers.dto.internal.FireEventRequest;
import com.omgservers.dto.internal.FireEventResponse;
import io.smallrye.mutiny.Uni;

public interface EventService {

    Uni<FireEventResponse> fireEvent(FireEventRequest request);
}
