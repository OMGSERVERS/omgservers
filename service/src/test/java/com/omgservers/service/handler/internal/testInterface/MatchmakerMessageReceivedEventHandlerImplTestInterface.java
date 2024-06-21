package com.omgservers.service.handler.internal.testInterface;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.handler.internal.MatchmakerMessageReceivedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class MatchmakerMessageReceivedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final MatchmakerMessageReceivedEventHandlerImpl matchmakerMessageReceivedEventHandler;

    public void handle(final EventModel event) {
        matchmakerMessageReceivedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
