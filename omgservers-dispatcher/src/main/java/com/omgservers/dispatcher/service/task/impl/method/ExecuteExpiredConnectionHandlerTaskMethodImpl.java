package com.omgservers.dispatcher.service.task.impl.method;

import com.omgservers.dispatcher.service.task.dto.ExecuteExpiredConnectionsHandlerTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteExpiredConnectionsHandlerTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ExecuteExpiredConnectionHandlerTaskMethodImpl implements ExecuteExpiredConnectionHandlerTaskMethod {

    final ExpiredConnectionsHandlerTaskImpl expiredConnectionsHandlerTask;

    @Override
    public Uni<ExecuteExpiredConnectionsHandlerTaskResponse> execute(
            final ExecuteExpiredConnectionsHandlerTaskRequest request) {
        log.trace("{}", request);

        return expiredConnectionsHandlerTask.execute()
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, {}:{}", t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecuteExpiredConnectionsHandlerTaskResponse::new);
    }
}
