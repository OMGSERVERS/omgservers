package com.omgservers.dispatcher.server.task.impl.method;

import com.omgservers.dispatcher.server.handler.HandlerService;
import com.omgservers.dispatcher.server.handler.dto.HandleIdleConnectionsRequest;
import com.omgservers.dispatcher.operation.Task;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class IdleConnectionsHandlerTaskImpl implements Task<IdleConnectionsHandlerTaskArguments> {

    final HandlerService handlerService;

    @Override
    public Uni<Boolean> execute(final IdleConnectionsHandlerTaskArguments taskArguments) {
        return handlerService.execute(new HandleIdleConnectionsRequest())
                .replaceWith(Boolean.FALSE);
    }
}
