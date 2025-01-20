package com.omgservers.service.service.task.impl.method.executeRelayTask;

import com.omgservers.service.operation.job.test.ExecuteTaskOperation;
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

    final ExecuteTaskOperation executeTaskOperation;

    final RelayTaskImpl relayTask;

    @Override
    public Uni<ExecuteRelayTaskResponse> execute(final ExecuteRelayTaskRequest request) {
        log.trace("{}", request);

        return executeTaskOperation.execute(relayTask.execute())
                .map(ExecuteRelayTaskResponse::new);
    }
}
