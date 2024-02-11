package com.omgservers.service.module.system.impl.service.eventService;

import com.omgservers.model.dto.system.SyncEventRequest;
import com.omgservers.model.dto.system.SyncEventResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface EventService {

    Uni<SyncEventResponse> syncEvent(@Valid SyncEventRequest request);
}
