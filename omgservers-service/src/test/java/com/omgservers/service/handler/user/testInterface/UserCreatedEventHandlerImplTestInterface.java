package com.omgservers.service.handler.user.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.user.UserCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UserCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final UserCreatedEventHandlerImpl userCreatedEventHandler;

    public void handle(final EventModel event) {
        userCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
