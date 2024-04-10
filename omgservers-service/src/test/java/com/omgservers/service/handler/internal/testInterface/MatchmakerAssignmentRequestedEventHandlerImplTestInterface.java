package com.omgservers.service.handler.internal.testInterface;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.handler.internal.MatchmakerAssignmentRequestedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerAssignmentRequestedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final MatchmakerAssignmentRequestedEventHandlerImpl matchmakerAssignmentRequestedEventHandler;

    public void handle(final EventModel event) {
        matchmakerAssignmentRequestedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
