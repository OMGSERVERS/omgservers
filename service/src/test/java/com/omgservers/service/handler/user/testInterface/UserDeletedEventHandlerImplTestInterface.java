package com.omgservers.service.handler.user.testInterface;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.handler.user.UserDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class UserDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final UserDeletedEventHandlerImpl userDeletedEventHandler;

    public void handle(final EventModel event) {
        userDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
