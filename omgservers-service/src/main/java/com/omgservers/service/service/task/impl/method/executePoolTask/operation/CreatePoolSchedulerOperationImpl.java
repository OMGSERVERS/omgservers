package com.omgservers.service.service.task.impl.method.executePoolTask.operation;

import com.omgservers.schema.model.poolServer.PoolServerStatusEnum;
import com.omgservers.service.service.task.impl.method.executePoolTask.component.PoolScheduler;
import com.omgservers.service.service.task.impl.method.executePoolTask.dto.FetchPoolResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class CreatePoolSchedulerOperationImpl implements CreatePoolSchedulerOperation {

    @Override
    public PoolScheduler execute(final FetchPoolResult fetchPoolResult) {
        final var poolScheduler = new PoolScheduler();
        fetchPoolResult.poolState().getPoolServers().stream()
                .filter(poolServer -> poolServer.getStatus().equals(PoolServerStatusEnum.CREATED))
                .forEach(poolScheduler::addPoolServer);
        fetchPoolResult.poolState().getPoolContainers().forEach(poolScheduler::addPoolContainer);

        return poolScheduler;
    }
}
