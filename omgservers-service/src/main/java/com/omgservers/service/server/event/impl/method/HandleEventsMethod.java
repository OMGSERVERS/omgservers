package com.omgservers.service.server.event.impl.method;

import com.omgservers.service.server.event.dto.HandleEventsRequest;
import com.omgservers.service.server.event.dto.HandleEventsResponse;
import io.smallrye.mutiny.Uni;

public interface HandleEventsMethod {
    Uni<HandleEventsResponse> execute(HandleEventsRequest request);
}
