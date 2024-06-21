package com.omgservers.service.module.system.impl.service.taskService.impl.method.executePoolTask;

import com.omgservers.model.dto.system.task.ExecutePoolTaskRequest;
import com.omgservers.model.dto.system.task.ExecutePoolTaskResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class ExecutePoolTaskMethodImpl implements ExecutePoolTaskMethod {

    final PoolTaskImpl poolTask;

    @Override
    public Uni<ExecutePoolTaskResponse> executePoolTask(final ExecutePoolTaskRequest request) {
        log.debug("Execute pool task, request={}", request);

        final var poolId = request.getPoolId();

        return poolTask.executeTask(poolId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, poolId={}, {}:{}",
                            poolId, t.getClass().getSimpleName(), t.getMessage());
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecutePoolTaskResponse::new);
    }
}
