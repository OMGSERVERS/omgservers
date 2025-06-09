package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchTenantStageResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.HandleTenantStageResult;

public interface DeleteClosedDeploymentsOperation {
    void execute(FetchTenantStageResult fetchTenantStageResult,
                 HandleTenantStageResult handleTenantStageResult);
}
