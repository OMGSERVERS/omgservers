package com.omgservers.service.handler.internal.testInterface;

import com.omgservers.schema.event.EventModel;
import com.omgservers.service.handler.internal.VersionBuildingFinishedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class VersionBuildingFinishedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final VersionBuildingFinishedEventHandlerImpl versionBuildingFinishedEventHandler;

    public void handle(final EventModel event) {
        versionBuildingFinishedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
