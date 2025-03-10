package com.omgservers.service.service.task.impl.method.executeEventHandlerTask;

import com.omgservers.service.operation.job.test.ExecuteTaskOperation;
import com.omgservers.service.service.task.dto.ExecuteEventHandlerTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteEventHandlerTaskResponse;
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
        log.trace("{}", request);

        return executeTaskOperation.execute(eventHandlerTask.execute())
                .map(ExecuteEventHandlerTaskResponse::new);
    }
}
