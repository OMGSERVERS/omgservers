package com.omgservers.module.internal.impl.service.handlerService.impl.method;

import com.omgservers.dto.internal.HandleEventRequest;
import com.omgservers.dto.internal.HandleEventResponse;
import io.smallrye.mutiny.Uni;

public interface HandleEventMethod {
    Uni<HandleEventResponse> handleEvent(HandleEventRequest request);
}
