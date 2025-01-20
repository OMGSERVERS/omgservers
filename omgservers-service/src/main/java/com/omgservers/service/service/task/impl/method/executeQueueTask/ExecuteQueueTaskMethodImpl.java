package com.omgservers.service.service.task.impl.method.executeQueueTask;

import com.omgservers.service.operation.job.test.ExecuteTaskOperation;
import com.omgservers.service.service.task.dto.ExecuteQueueTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteQueueTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecuteQueueTaskMethodImpl implements ExecuteQueueTaskMethod {

    final ExecuteTaskOperation executeTaskOperation;

    final QueueTaskImpl queueTask;

    @Override
    public Uni<ExecuteQueueTaskResponse> execute(final ExecuteQueueTaskRequest request) {
        log.trace("{}", request);

        final var queueId = request.getQueueId();

        return executeTaskOperation.execute(queueTask.execute(queueId))
                .map(ExecuteQueueTaskResponse::new);
    }
}
