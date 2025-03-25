package com.omgservers.service.service.task.impl.method.executePoolTask.operation;

import com.omgservers.schema.model.poolChangeOfState.PoolChangeOfStateDto;
import com.omgservers.service.service.task.impl.method.executePoolTask.dto.FetchPoolResult;
import com.omgservers.service.service.task.impl.method.executePoolTask.dto.HandlePoolResult;
import com.omgservers.service.service.task.impl.method.executePoolTask.operation.handleDeploymentCommands.HandlePoolCommandsOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandlePoolOperationImpl implements HandlePoolOperation {

    final HandlePoolRequestsOperation handlePoolRequestsOperation;
    final HandlePoolCommandsOperation handlePoolCommandsOperation;

    @Override
    public HandlePoolResult execute(final FetchPoolResult fetchPoolResult) {
        final var poolId = fetchPoolResult.poolId();
        final var handlePoolResult = new HandlePoolResult(poolId, new PoolChangeOfStateDto());

        handlePoolCommandsOperation.execute(fetchPoolResult, handlePoolResult);
        handlePoolRequestsOperation.execute(fetchPoolResult, handlePoolResult);

        return handlePoolResult;
    }
}
