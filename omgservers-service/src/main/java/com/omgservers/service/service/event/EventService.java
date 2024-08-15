package com.omgservers.service.service.event;

import com.omgservers.service.service.event.dto.HandleEventRequest;
import com.omgservers.service.service.event.dto.HandleEventResponse;
import com.omgservers.service.service.event.dto.RelayEventsRequest;
import com.omgservers.service.service.event.dto.RelayEventsResponse;
import com.omgservers.service.service.event.dto.SyncEventRequest;
import com.omgservers.service.service.event.dto.SyncEventResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface EventService {

    Uni<SyncEventResponse> syncEvent(@Valid SyncEventRequest request);

    Uni<SyncEventResponse> syncEventWithIdempotency(@Valid SyncEventRequest request);

    Uni<RelayEventsResponse> relayEvents(@Valid RelayEventsRequest request);

    Uni<HandleEventResponse> handleEvent(@Valid HandleEventRequest request);
}
