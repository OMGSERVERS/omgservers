package com.omgservers.service.server.task.impl.method.executePoolTask;

import com.omgservers.service.operation.job.ExecuteTaskOperation;
import com.omgservers.service.server.task.dto.ExecutePoolTaskRequest;
import com.omgservers.service.server.task.dto.ExecutePoolTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecutePoolTaskMethodImpl implements ExecutePoolTaskMethod {

    final ExecuteTaskOperation executeTaskOperation;

    final PoolTaskImpl poolTask;

    @Override
    public Uni<ExecutePoolTaskResponse> execute(final ExecutePoolTaskRequest request) {
        log.trace("{}", request);

        final var poolId = request.getPoolId();
        final var taskArguments = new PoolTaskArguments(poolId);
        return executeTaskOperation.executeFailSafe(poolTask, taskArguments)
                .map(ExecutePoolTaskResponse::new);
    }
}
