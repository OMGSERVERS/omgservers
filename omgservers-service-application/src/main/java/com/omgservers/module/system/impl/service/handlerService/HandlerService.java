package com.omgservers.module.system.impl.service.handlerService;

import com.omgservers.model.dto.internal.HandleEventRequest;
import com.omgservers.model.dto.internal.HandleEventResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface HandlerService {

    Uni<HandleEventResponse> handleEvent(HandleEventRequest request);

    default HandleEventResponse handleEvent(long timeout, HandleEventRequest request) {
        return handleEvent(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
