package com.omgservers.base.module.internal.impl.service.handlerService.impl.method;

import com.omgservers.dto.internalModule.HandleEventRequest;
import com.omgservers.dto.internalModule.HandleEventResponse;
import io.smallrye.mutiny.Uni;

public interface HandleEventMethod {
    Uni<HandleEventResponse> handleEvent(HandleEventRequest request);
}
