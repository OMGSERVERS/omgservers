package com.omgservers.service.service.task.impl.method.executeRelayTask;

import com.omgservers.service.service.task.dto.ExecuteRelayTaskRequest;
import com.omgservers.service.service.task.dto.ExecuteRelayTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecuteRelayTaskMethodImpl implements ExecuteRelayTaskMethod {

    final RelayTaskImpl relayTask;

    @Override
    public Uni<ExecuteRelayTaskResponse> executeRelayTask(final ExecuteRelayTaskRequest request) {
        log.debug("Execute relay task, request={}", request);

        return relayTask.executeTask()
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, {}:{}", t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .invoke(result -> log.info("Task finished, result={}", result))
                .map(ExecuteRelayTaskResponse::new);
    }
}
