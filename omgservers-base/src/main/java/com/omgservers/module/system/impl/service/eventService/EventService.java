package com.omgservers.module.system.impl.service.eventService;

import com.omgservers.dto.internal.FireEventRequest;
import com.omgservers.dto.internal.FireEventResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface EventService {

    Uni<FireEventResponse> fireEvent(@Valid FireEventRequest request);
}
