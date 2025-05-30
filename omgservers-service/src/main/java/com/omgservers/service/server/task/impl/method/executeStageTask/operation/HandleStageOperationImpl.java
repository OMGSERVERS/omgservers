package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchStageResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.HandleStageResult;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleStageOperationImpl implements HandleStageOperation {

    final ClosePreviouslyCreatedDeploymentsOperation closePreviouslyCreatedDeploymentsOperation;
    final DeleteClosedDeploymentsOperation deleteClosedDeploymentsOperation;

    @Override
    public HandleStageResult execute(final FetchStageResult fetchStageResult) {
        final var handleStageResult = new HandleStageResult(new ArrayList<>(), new ArrayList<>());

        deleteClosedDeploymentsOperation.execute(fetchStageResult, handleStageResult);

        closePreviouslyCreatedDeploymentsOperation.execute(fetchStageResult, handleStageResult);

        return handleStageResult;
    }
}
