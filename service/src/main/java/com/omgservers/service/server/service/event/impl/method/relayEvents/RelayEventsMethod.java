package com.omgservers.service.server.service.event.impl.method.relayEvents;

import com.omgservers.schema.service.system.RelayEventsRequest;
import com.omgservers.schema.service.system.RelayEventsResponse;
import io.smallrye.mutiny.Uni;

public interface RelayEventsMethod {
    Uni<RelayEventsResponse> relayEvents(RelayEventsRequest request);
}
