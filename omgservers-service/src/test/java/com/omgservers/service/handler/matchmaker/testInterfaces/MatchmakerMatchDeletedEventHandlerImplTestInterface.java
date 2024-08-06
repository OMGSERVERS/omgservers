package com.omgservers.service.handler.matchmaker.testInterfaces;

import com.omgservers.schema.event.EventModel;
import com.omgservers.service.handler.matchmaker.MatchmakerMatchCreatedEventHandlerImpl;
import com.omgservers.service.handler.matchmaker.MatchmakerMatchDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final MatchmakerMatchDeletedEventHandlerImpl matchmakerMatchDeletedEventHandler;

    public void handle(final EventModel event) {
        matchmakerMatchDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
