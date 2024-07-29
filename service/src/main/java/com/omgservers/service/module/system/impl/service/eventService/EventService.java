package com.omgservers.service.module.system.impl.service.eventService;

import com.omgservers.schema.service.system.HandleEventRequest;
import com.omgservers.schema.service.system.HandleEventResponse;
import com.omgservers.schema.service.system.RelayEventsRequest;
import com.omgservers.schema.service.system.RelayEventsResponse;
import com.omgservers.schema.service.system.SyncEventRequest;
import com.omgservers.schema.service.system.SyncEventResponse;
import io.smallrye.mutiny.Uni;
import jakarta.validation.Valid;

public interface EventService {

    Uni<SyncEventResponse> syncEvent(@Valid SyncEventRequest request);

    Uni<SyncEventResponse> syncEventWithIdempotency(@Valid SyncEventRequest request);

    Uni<RelayEventsResponse> relayEvents(@Valid RelayEventsRequest request);

    Uni<HandleEventResponse> handleEvent(@Valid HandleEventRequest request);
}
