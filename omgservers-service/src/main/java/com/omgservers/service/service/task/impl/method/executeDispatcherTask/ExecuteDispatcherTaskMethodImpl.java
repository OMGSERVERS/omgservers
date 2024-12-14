package com.omgservers.service.service.task.impl.method.executeDispatcherTask;

import com.omgservers.service.service.task.dto.ExecuteDispatcherTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteDispatcherTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ExecuteDispatcherTaskMethodImpl implements ExecuteDispatcherTaskMethod {

    final DispatcherTaskImpl dispatcherTask;

    @Override
    public Uni<ExecuteDispatcherTaskResponse> execute(final ExecuteDispatcherTaskRequest request) {
        log.trace("Requested, {}", request);

        return dispatcherTask.execute()
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, {}:{}", t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .invoke(result -> log.debug("Task finished, result={}", result))
                .map(ExecuteDispatcherTaskResponse::new);
    }
}
