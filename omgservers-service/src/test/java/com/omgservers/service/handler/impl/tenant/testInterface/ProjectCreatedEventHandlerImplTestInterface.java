package com.omgservers.service.handler.impl.tenant.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.tenant.TenantProjectCreatedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ProjectCreatedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final TenantProjectCreatedEventHandlerImpl projectCreatedEventHandler;

    public void handle(final EventModel event) {
        projectCreatedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
