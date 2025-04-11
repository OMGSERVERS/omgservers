package com.omgservers.dispatcher.service.task.impl.method;

import com.omgservers.dispatcher.service.handler.HandlerService;
import com.omgservers.dispatcher.service.handler.dto.HandleIdleConnectionsRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class IdleConnectionsHandlerTaskImpl {

    final HandlerService handlerService;

    public Uni<Boolean> execute() {
        return handlerService.execute(new HandleIdleConnectionsRequest())
                .replaceWith(Boolean.TRUE);
    }
}
