package com.omgservers.dispatcher.service.task.impl.method;

import com.omgservers.dispatcher.service.task.dto.ExecuteRefreshDispatcherTokenTaskRequest;
import com.omgservers.dispatcher.service.task.dto.ExecuteRefreshDispatcherTokenTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class ExecuteRefreshDispatcherTokenTaskMethodImpl implements ExecuteRefreshDispatcherTokenTaskMethod {

    final RefreshDispatcherTokenTaskImpl refreshDispatcherTokenTask;

    @Override
    public Uni<ExecuteRefreshDispatcherTokenTaskResponse> execute(
            final ExecuteRefreshDispatcherTokenTaskRequest request) {
        log.trace("{}", request);

        return refreshDispatcherTokenTask.execute()
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, {}:{}", t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecuteRefreshDispatcherTokenTaskResponse::new);
    }
}
