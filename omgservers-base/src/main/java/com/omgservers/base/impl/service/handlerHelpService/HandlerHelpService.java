package com.omgservers.base.impl.service.handlerHelpService;

import com.omgservers.base.impl.service.handlerHelpService.request.HandleEventHelpRequest;
import com.omgservers.base.impl.service.handlerHelpService.response.HandleEventHelpResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface HandlerHelpService {

    Uni<HandleEventHelpResponse> handleEvent(HandleEventHelpRequest request);

    default HandleEventHelpResponse handleEvent(long timeout, HandleEventHelpRequest request) {
        return handleEvent(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
