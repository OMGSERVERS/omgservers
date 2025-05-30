package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchStageResult;
import io.smallrye.mutiny.Uni;

public interface FetchStageOperation {
    Uni<FetchStageResult> execute(Long tenantId, Long tenantStageId);
}
