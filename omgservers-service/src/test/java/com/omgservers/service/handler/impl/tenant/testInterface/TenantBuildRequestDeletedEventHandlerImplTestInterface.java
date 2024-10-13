package com.omgservers.service.handler.impl.tenant.testInterface;

import com.omgservers.service.event.EventModel;
import com.omgservers.service.handler.impl.tenant.TenantBuildRequestDeletedEventHandlerImpl;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class TenantBuildRequestDeletedEventHandlerImplTestInterface {
    private static final long TIMEOUT = 1L;

    final TenantBuildRequestDeletedEventHandlerImpl tenantBuildRequestDeletedEventHandler;

    public void handle(final EventModel event) {
        tenantBuildRequestDeletedEventHandler.handle(event)
                .await().atMost(Duration.ofSeconds(TIMEOUT));
    }
}
