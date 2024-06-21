package com.omgservers.service.module.system.impl.service.taskService.impl.method.executeRuntimeTask;

import com.omgservers.model.dto.system.task.ExecuteRuntimeTaskRequest;
import com.omgservers.model.dto.system.task.ExecuteRuntimeTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecuteRuntimeTaskMethodImpl implements ExecuteRuntimeTaskMethod {

    final RuntimeTaskImpl runtimeTask;

    @Override
    public Uni<ExecuteRuntimeTaskResponse> executeRuntimeTask(final ExecuteRuntimeTaskRequest request) {
        log.debug("Execute runtime task, request={}", request);

        final var runtimeId = request.getRuntimeId();

        return runtimeTask.executeTask(runtimeId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, runtimeId={}, {}:{}",
                            runtimeId, t.getClass().getSimpleName(), t.getMessage());
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecuteRuntimeTaskResponse::new);
    }
}
