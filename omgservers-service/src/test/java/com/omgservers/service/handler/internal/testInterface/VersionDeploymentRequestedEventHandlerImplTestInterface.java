package com.omgservers.service.handler.internal.testInterface;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.handler.internal.VersionDeploymentRequestedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionDeploymentRequestedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final VersionDeploymentRequestedEventHandlerImpl versionDeploymentRequestedEventHandler;

    public void handle(final EventModel event) {
        versionDeploymentRequestedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
