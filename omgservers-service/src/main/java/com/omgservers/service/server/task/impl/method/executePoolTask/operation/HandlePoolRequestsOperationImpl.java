package com.omgservers.service.server.task.impl.method.executePoolTask.operation;

import com.omgservers.service.server.task.impl.method.executePoolTask.dto.FetchPoolResult;
import com.omgservers.service.server.task.impl.method.executePoolTask.dto.HandlePoolResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@ApplicationScoped
@AllArgsConstructor
class HandlePoolRequestsOperationImpl implements HandlePoolRequestsOperation {

    final CreatePoolSchedulerOperation createPoolSchedulerOperation;
    final CreatePoolContainerOperation createPoolContainerOperation;

    @Override
    public void execute(final FetchPoolResult fetchPoolResult,
                        final HandlePoolResult handlePoolResult) {
        final var poolScheduler = createPoolSchedulerOperation.execute(fetchPoolResult);

        final var failures = new AtomicInteger();

        fetchPoolResult.poolState().getPoolRequests()
                .forEach(poolRequest -> {
                    final var poolRequestId = poolRequest.getId();
                    final var isNotForDelete = !handlePoolResult.poolChangeOfState().getPoolRequestsToDelete()
                            .contains(poolRequestId);

                    if (isNotForDelete) {
                        final var poolServerOptional = poolScheduler.schedule(poolRequest);
                        if (poolServerOptional.isPresent()) {
                            final var poolServer = poolServerOptional.get();
                            final var poolContainer = createPoolContainerOperation.execute(poolServer, poolRequest);

                            handlePoolResult.poolChangeOfState().getPoolContainersToSync().add(poolContainer);
                            handlePoolResult.poolChangeOfState().getPoolRequestsToDelete().add(poolRequest.getId());
                        } else {
                            failures.incrementAndGet();
                        }
                    }
                });

        if (failures.get() > 0) {
            log.error("Failed to schedule \"{}\" container/s", failures);
        }
    }

}
