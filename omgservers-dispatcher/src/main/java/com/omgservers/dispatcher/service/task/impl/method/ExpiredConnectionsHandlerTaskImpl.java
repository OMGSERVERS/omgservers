package com.omgservers.dispatcher.service.task.impl.method;

import com.omgservers.dispatcher.service.handler.HandlerService;
import com.omgservers.dispatcher.service.handler.dto.HandleExpiredConnectionsRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExpiredConnectionsHandlerTaskImpl {

    final HandlerService handlerService;

    public Uni<Boolean> execute() {
        return handlerService.handleExpiredConnections(new HandleExpiredConnectionsRequest())
                .replaceWith(Boolean.TRUE);
    }
}
