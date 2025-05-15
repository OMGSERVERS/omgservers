package com.omgservers.service.server.task.impl.method.executeEventHandlerTask;

import com.omgservers.service.operation.task.ExecuteTaskOperation;
import com.omgservers.service.server.task.dto.ExecuteEventHandlerTaskRequest;
import com.omgservers.service.server.task.dto.ExecuteEventHandlerTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecuteEventHandlerTaskMethodImpl implements ExecuteEventHandlerTaskMethod {

    final ExecuteTaskOperation executeTaskOperation;

    final EventHandlerTaskImpl eventHandlerTask;

    @Override
    public Uni<ExecuteEventHandlerTaskResponse> execute(final ExecuteEventHandlerTaskRequest request) {
        log.debug("{}", request);

        final var taskArguments = new EventHandlerTaskArguments();
        return executeTaskOperation.executeFailSafe(eventHandlerTask, taskArguments)
                .map(ExecuteEventHandlerTaskResponse::new);
    }
}
