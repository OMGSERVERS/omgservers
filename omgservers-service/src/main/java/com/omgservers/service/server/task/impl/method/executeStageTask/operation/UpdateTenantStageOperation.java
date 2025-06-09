package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.service.server.task.impl.method.executeStageTask.dto.HandleTenantStageResult;
import io.smallrye.mutiny.Uni;

public interface UpdateTenantStageOperation {
    Uni<Void> execute(HandleTenantStageResult handleTenantStageResult);
}
