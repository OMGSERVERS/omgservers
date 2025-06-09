package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchTenantStageResult;
import io.smallrye.mutiny.Uni;

public interface FetchStageOperation {
    Uni<FetchTenantStageResult> execute(Long tenantId, Long tenantStageId);
}
