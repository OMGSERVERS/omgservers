package com.omgservers.service.server.event;

import com.omgservers.service.server.event.dto.HandleEventRequest;
import com.omgservers.service.server.event.dto.HandleEventResponse;
import com.omgservers.service.server.event.dto.HandleEventsRequest;
import com.omgservers.service.server.event.dto.HandleEventsResponse;
import com.omgservers.service.server.event.dto.SyncEventRequest;
import com.omgservers.service.server.event.dto.SyncEventResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface EventService {

    Uni<SyncEventResponse> syncEvent(@Valid SyncEventRequest request);

    Uni<SyncEventResponse> syncEventWithIdempotency(@Valid SyncEventRequest request);

    Uni<HandleEventsResponse> handleEvents(@Valid HandleEventsRequest request);

    Uni<HandleEventResponse> handleEvent(@Valid HandleEventRequest request);
}
