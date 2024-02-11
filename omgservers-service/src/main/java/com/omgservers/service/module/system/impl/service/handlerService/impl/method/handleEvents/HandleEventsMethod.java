package com.omgservers.service.module.system.impl.service.handlerService.impl.method.handleEvents;

import com.omgservers.model.dto.system.HandleEventsRequest;
import com.omgservers.model.dto.system.HandleEventsResponse;
import io.smallrye.mutiny.Uni;

public interface HandleEventsMethod {
    Uni<HandleEventsResponse> handleEvents(HandleEventsRequest request);
}
