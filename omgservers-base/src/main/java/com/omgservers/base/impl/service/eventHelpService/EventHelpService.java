package com.omgservers.base.impl.service.eventHelpService;

import com.omgservers.base.impl.service.eventHelpService.request.FireEventHelpRequest;
import com.omgservers.base.impl.service.eventHelpService.response.FireEventHelpResponse;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface EventHelpService {

    Uni<Void> startEventDispatcher();

    Uni<FireEventHelpResponse> fireEvent(FireEventHelpRequest request);

    default FireEventHelpResponse fireEvent(long timeout, FireEventHelpRequest request) {
        return fireEvent(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
