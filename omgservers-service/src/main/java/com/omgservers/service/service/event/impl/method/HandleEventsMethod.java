package com.omgservers.service.service.event.impl.method;

import com.omgservers.service.service.event.dto.HandleEventsRequest;
import com.omgservers.service.service.event.dto.HandleEventsResponse;
import io.smallrye.mutiny.Uni;

public interface HandleEventsMethod {
    Uni<HandleEventsResponse> execute(HandleEventsRequest request);
}
