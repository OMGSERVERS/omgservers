package com.omgservers.service.server.task.impl.method.executeStageTask.operation.handleTenantStageCommands;

import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchTenantStageResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.HandleTenantStageResult;

public interface HandleTenantStageCommandsOperation {
    void execute(FetchTenantStageResult fetchTenantStageResult,
                 HandleTenantStageResult handleTenantStageResult);
}