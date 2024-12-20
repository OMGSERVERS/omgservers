package com.omgservers.service.service.task.impl.method.executeRuntimeTask;

import com.omgservers.service.service.task.dto.ExecuteRuntimeTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteRuntimeTaskResponse;
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
    public Uni<ExecuteRuntimeTaskResponse> execute(final ExecuteRuntimeTaskRequest request) {
        log.trace("Requested, {}", request);

        final var runtimeId = request.getRuntimeId();

        return runtimeTask.execute(runtimeId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, runtimeId={}, {}:{}",
                            runtimeId, t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecuteRuntimeTaskResponse::new);
    }
}
