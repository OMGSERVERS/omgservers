package com.omgservers.dispatcher.service.task.impl.method;

import com.omgservers.dispatcher.service.dispatcher.DispatcherService;
import com.omgservers.dispatcher.service.dispatcher.dto.HandleExpiredConnectionsRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DispatcherTaskImpl {

    final DispatcherService dispatcherService;

    public Uni<Boolean> execute() {
        return dispatcherService.handleExpiredConnections(new HandleExpiredConnectionsRequest())
                .repeat().withDelay(Duration.ofSeconds(1)).indefinitely()
                .collect().last()
                .replaceWith(Boolean.TRUE);
    }
}
