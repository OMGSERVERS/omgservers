package com.omgservers.service.service.task.impl.method.executeBootstrapTask;

import com.omgservers.service.service.task.dto.ExecuteBootstrapTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteBootstrapTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ExecuteBootstrapTaskMethodImpl implements ExecuteBootstrapTaskMethod {

    final BootstrapTaskImpl bootstrapTask;

    @Override
    public Uni<ExecuteBootstrapTaskResponse> execute(final ExecuteBootstrapTaskRequest request) {
        log.trace("{}", request);

        return bootstrapTask.execute()
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, {}:{}", t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .invoke(result -> log.debug("Task finished, result={}", result))
                .map(ExecuteBootstrapTaskResponse::new);
    }
}
