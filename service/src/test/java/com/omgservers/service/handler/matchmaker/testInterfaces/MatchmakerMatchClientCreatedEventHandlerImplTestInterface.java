package com.omgservers.service.handler.matchmaker.testInterfaces;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.handler.matchmaker.MatchmakerMatchClientCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMatchClientCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final MatchmakerMatchClientCreatedEventHandlerImpl matchmakerMatchClientCreatedEventHandler;

    public void handle(final EventModel event) {
        matchmakerMatchClientCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
