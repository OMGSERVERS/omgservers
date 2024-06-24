package com.omgservers.service.handler.tenant.testInterface;

import com.omgservers.model.event.EventModel;
import com.omgservers.service.handler.tenant.StageDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class StageDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final StageDeletedEventHandlerImpl stageDeletedEventHandler;

    public void handle(final EventModel event) {
        stageDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}