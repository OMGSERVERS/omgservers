package com.omgservers.service.handler.impl.runtime.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.runtime.RuntimeAssignmentDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class RuntimeAssignmentDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final RuntimeAssignmentDeletedEventHandlerImpl runtimeAssignmentDeletedEventHandler;

    public void handle(final EventModel event) {
        runtimeAssignmentDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
