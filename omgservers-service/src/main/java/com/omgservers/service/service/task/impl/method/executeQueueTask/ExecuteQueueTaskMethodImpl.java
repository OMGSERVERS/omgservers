package com.omgservers.service.service.task.impl.method.executeQueueTask;

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

    final QueueTaskImpl queueTask;

    @Override
    public Uni<ExecuteQueueTaskResponse> execute(final ExecuteQueueTaskRequest request) {
        log.trace("{}", request);

        final var queueId = request.getQueueId();

        return queueTask.execute(queueId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, poolId={}, {}:{}",
                            queueId, t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecuteQueueTaskResponse::new);
    }
}
