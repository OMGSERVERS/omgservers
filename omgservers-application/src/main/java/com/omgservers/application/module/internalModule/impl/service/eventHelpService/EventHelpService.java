package com.omgservers.application.module.internalModule.impl.service.eventHelpService;

import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.FireEventHelpRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface EventHelpService {

    Uni<Void> startEventDispatcher();

    Uni<Void> fireEvent(FireEventHelpRequest request);

    default void fireEvent(long timeout, FireEventHelpRequest request) {
        fireEvent(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
