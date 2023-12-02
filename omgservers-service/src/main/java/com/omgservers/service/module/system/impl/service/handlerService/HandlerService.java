package com.omgservers.service.module.system.impl.service.handlerService;

import com.omgservers.model.dto.system.HandleEventRequest;
import com.omgservers.model.dto.system.HandleEventResponse;
import io.smallrye.mutiny.Uni;

public interface HandlerService {

    Uni<HandleEventResponse> handleEvent(HandleEventRequest request);
}
