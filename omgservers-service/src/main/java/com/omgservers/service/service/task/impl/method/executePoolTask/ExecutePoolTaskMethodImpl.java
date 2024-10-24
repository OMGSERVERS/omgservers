package com.omgservers.service.service.task.impl.method.executePoolTask;

import com.omgservers.service.service.task.dto.ExecutePoolTaskRequest;
import com.omgservers.service.service.task.dto.ExecutePoolTaskResponse;
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
    public Uni<ExecutePoolTaskResponse> execute(final ExecutePoolTaskRequest request) {
        log.debug("Requested, {}", request);

        final var poolId = request.getPoolId();

        return poolTask.execute(poolId)
                .onFailure()
                .recoverWithUni(t -> {
                    log.warn("Job task failed, poolId={}, {}:{}",
                            poolId, t.getClass().getSimpleName(), t.getMessage(), t);
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .map(ExecutePoolTaskResponse::new);
    }
}
