package com.omgservers.service.handler.matchmaker.testInterfaces;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.matchmaker.MatchmakerMatchAssignmentCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchAssignmentCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final MatchmakerMatchAssignmentCreatedEventHandlerImpl handler;

    public void handle(final EventModel event) {
        handler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
