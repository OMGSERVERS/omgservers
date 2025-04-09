package com.omgservers.service.server.task.impl.method.executePoolTask;

import com.omgservers.service.server.task.Task;
import com.omgservers.service.server.task.impl.method.executePoolTask.operation.FetchPoolOperation;
import com.omgservers.service.server.task.impl.method.executePoolTask.operation.HandlePoolOperation;
import com.omgservers.service.server.task.impl.method.executePoolTask.operation.UpdatePoolOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
public class PoolTaskImpl implements Task<PoolTaskArguments> {

    final HandlePoolOperation handlePoolOperation;
    final UpdatePoolOperation updatePoolOperation;
    final FetchPoolOperation fetchPoolOperation;

    public Uni<Boolean> execute(final PoolTaskArguments taskArguments) {
        final var poolId = taskArguments.poolId();

        return fetchPoolOperation.execute(poolId)
                .map(handlePoolOperation::execute)
                .invoke(handlePoolResult -> {
                    final var poolChangeOfState = handlePoolResult.poolChangeOfState();
                    if (poolChangeOfState.isNotEmpty()) {
                        log.info("Update pool state, poolId={}, {}",
                                poolId, poolChangeOfState);
                    }
                })
                .flatMap(updatePoolOperation::execute)
                .replaceWith(Boolean.TRUE);
    }
}
