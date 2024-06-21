package com.omgservers.service.module.system.impl.service.eventService.impl.method.relayEvents;

import com.omgservers.model.dto.system.RelayEventsRequest;
import com.omgservers.model.dto.system.RelayEventsResponse;
import io.smallrye.mutiny.Uni;

public interface RelayEventsMethod {
    Uni<RelayEventsResponse> relayEvents(RelayEventsRequest request);
}
