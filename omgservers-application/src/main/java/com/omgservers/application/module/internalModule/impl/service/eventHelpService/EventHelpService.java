package com.omgservers.application.module.internalModule.impl.service.eventHelpService;

import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.FireEventHelpRequest;
import com.omgservers.application.module.internalModule.impl.service.eventHelpService.request.InsertEventHelpRequest;
import io.smallrye.mutiny.Uni;

import java.time.Duration;

public interface EventHelpService {

    Uni<Void> startEventDispatcher();

    Uni<Void> insertEvent(InsertEventHelpRequest request);

    default void insertEvent(long timeout, InsertEventHelpRequest request) {
        insertEvent(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }

    Uni<Void> fireEvent(FireEventHelpRequest request);

    default void fireEvent(long timeout, FireEventHelpRequest request) {
        fireEvent(request)
                .await().atMost(Duration.ofSeconds(timeout));
    }
}
