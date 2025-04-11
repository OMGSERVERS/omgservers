package com.omgservers.dispatcher.service.task.impl.method;

import com.omgservers.dispatcher.service.task.dto.ExecuteIdleConnectionsHandlerTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteIdleConnectionsHandlerTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ExecuteIdleConnectionHandlerTaskMethodImpl implements ExecuteIdleConnectionHandlerTaskMethod {

    final IdleConnectionsHandlerTaskImpl idleConnectionsHandlerTask;

    @Override
    public Uni<ExecuteIdleConnectionsHandlerTaskResponse> execute(
            final ExecuteIdleConnectionsHandlerTaskRequest request) {
        log.trace("{}", request);

        return idleConnectionsHandlerTask.execute()
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, {}:{}", t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecuteIdleConnectionsHandlerTaskResponse::new);
    }
}
