package com.omgservers.connector.server.task.impl.method.executeIdleConnectionHandlerTask;

import com.omgservers.connector.operation.Task;
import com.omgservers.connector.server.handler.HandlerService;
import com.omgservers.connector.server.handler.dto.HandleIdleConnectionsRequest;
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
