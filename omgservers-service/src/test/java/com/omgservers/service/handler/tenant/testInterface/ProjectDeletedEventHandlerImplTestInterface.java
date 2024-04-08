package com.omgservers.service.handler.tenant.testInterface;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.handler.tenant.ProjectDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ProjectDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final ProjectDeletedEventHandlerImpl projectDeletedEventHandler;

    public void handle(final EventModel event) {
        projectDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
