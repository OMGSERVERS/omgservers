package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.schema.model.tenantStageChangeOfState.TenantStageChangeOfStateDto;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchTenantStageResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.HandleTenantStageResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.operation.handleTenantStageCommands.HandleTenantStageCommandsOperation;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = lombok.AccessLevel.PACKAGE)
class HandleStageOperationImpl implements HandleStageOperation {

    final ClosePreviouslyCreatedDeploymentsOperation closePreviouslyCreatedDeploymentsOperation;
    final HandleTenantStageCommandsOperation handleTenantStageCommandsOperation;
    final DeleteClosedDeploymentsOperation deleteClosedDeploymentsOperation;

    @Override
    public HandleTenantStageResult execute(final FetchTenantStageResult fetchTenantStageResult) {
        final var tenantId = fetchTenantStageResult.tenantStageState().getTenantStage().getTenantId();
        final var tenantStageId = fetchTenantStageResult.tenantStageState().getTenantStage().getId();
        final var handleStageResult = new HandleTenantStageResult(tenantId,
                tenantStageId,
                new TenantStageChangeOfStateDto());

        deleteClosedDeploymentsOperation.execute(fetchTenantStageResult, handleStageResult);

        closePreviouslyCreatedDeploymentsOperation.execute(fetchTenantStageResult, handleStageResult);

        handleTenantStageCommandsOperation.execute(fetchTenantStageResult, handleStageResult);

        return handleStageResult;
    }
}
