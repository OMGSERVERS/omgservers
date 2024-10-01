package com.omgservers.service.handler.matchmaker.testInterfaces;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.matchmaker.MatchmakerDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final MatchmakerDeletedEventHandlerImpl matchmakerDeletedEventHandler;

    public void handle(final EventModel event) {
        matchmakerDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
