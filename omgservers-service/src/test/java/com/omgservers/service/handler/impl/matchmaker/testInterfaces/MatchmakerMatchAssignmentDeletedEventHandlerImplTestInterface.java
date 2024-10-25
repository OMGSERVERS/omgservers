package com.omgservers.service.handler.impl.matchmaker.testInterfaces;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.matchmaker.MatchmakerMatchAssignmentDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchAssignmentDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final MatchmakerMatchAssignmentDeletedEventHandlerImpl handler;

    public void handle(final EventModel event) {
        handler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
