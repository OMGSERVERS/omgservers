package com.omgservers.service.service.event.impl.method.relayEvents;

import com.omgservers.service.service.event.dto.RelayEventsRequest;
import com.omgservers.service.service.event.dto.RelayEventsResponse;
import io.smallrye.mutiny.Uni;

public interface RelayEventsMethod {
    Uni<RelayEventsResponse> relayEvents(RelayEventsRequest request);
}
