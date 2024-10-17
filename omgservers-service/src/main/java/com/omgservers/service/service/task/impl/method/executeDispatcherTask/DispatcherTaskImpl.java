package com.omgservers.service.service.task.impl.method.executeDispatcherTask;

import com.omgservers.service.module.dispatcher.DispatcherModule;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleExpiredConnectionsRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class DispatcherTaskImpl {

    final DispatcherModule dispatcherModule;

    public Uni<Boolean> execute() {
        return dispatcherModule.getDispatcherService().handleExpiredConnections(new HandleExpiredConnectionsRequest())
                .repeat().withDelay(Duration.ofSeconds(1)).indefinitely()
                .collect().last()
                .replaceWith(Boolean.TRUE);
    }
}
