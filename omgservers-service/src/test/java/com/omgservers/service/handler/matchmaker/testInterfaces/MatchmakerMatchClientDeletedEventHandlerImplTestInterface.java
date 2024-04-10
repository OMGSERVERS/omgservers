package com.omgservers.service.handler.matchmaker.testInterfaces;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.handler.matchmaker.MatchmakerMatchClientDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchClientDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final MatchmakerMatchClientDeletedEventHandlerImpl matchmakerMatchClientDeletedEventHandler;

    public void handle(final EventModel event) {
        matchmakerMatchClientDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
