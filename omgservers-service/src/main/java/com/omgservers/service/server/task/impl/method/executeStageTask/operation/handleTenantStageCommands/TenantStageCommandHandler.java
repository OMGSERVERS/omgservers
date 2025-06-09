package com.omgservers.service.server.task.impl.method.executeStageTask.operation.handleTenantStageCommands;

import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandModel;
import com.omgservers.schema.model.tenantStageCommand.TenantStageCommandQualifierEnum;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchTenantStageResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.HandleTenantStageResult;

public interface TenantStageCommandHandler {

    TenantStageCommandQualifierEnum getQualifier();

    boolean handle(FetchTenantStageResult fetchTenantStageResult,
                   HandleTenantStageResult handleTenantStageResult,
                   TenantStageCommandModel tenantStageCommand);
}