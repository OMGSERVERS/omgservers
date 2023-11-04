package com.omgservers.service.module.system.impl.service.handlerService.impl.method;

import com.omgservers.model.dto.system.HandleEventRequest;
import com.omgservers.model.dto.system.HandleEventResponse;
import io.smallrye.mutiny.Uni;

public interface HandleEventMethod {
    Uni<HandleEventResponse> handleEvent(HandleEventRequest request);
}
