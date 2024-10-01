package com.omgservers.service.handler.internal.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.internal.RuntimeDeploymentRequestedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeDeploymentRequestedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final RuntimeDeploymentRequestedEventHandlerImpl runtimeDeploymentRequestedEventHandler;

    public void handle(final EventModel event) {
        runtimeDeploymentRequestedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
