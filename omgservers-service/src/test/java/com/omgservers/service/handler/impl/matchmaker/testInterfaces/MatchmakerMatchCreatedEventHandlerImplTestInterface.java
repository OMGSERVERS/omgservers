package com.omgservers.service.handler.impl.matchmaker.testInterfaces;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.matchmaker.MatchmakerMatchCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final MatchmakerMatchCreatedEventHandlerImpl matchmakerMatchCreatedEventHandlerImpl;

    public void handle(final EventModel event) {
        matchmakerMatchCreatedEventHandlerImpl.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}