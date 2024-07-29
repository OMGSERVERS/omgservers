package com.omgservers.service.handler.matchmaker.testInterfaces;

import com.omgservers.schema.event.EventModel;
import com.omgservers.service.handler.matchmaker.MatchmakerAssignmentCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerAssignmentCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final MatchmakerAssignmentCreatedEventHandlerImpl matchmakerAssignmentCreatedEventHandler;

    public void handle(final EventModel event) {
        matchmakerAssignmentCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
