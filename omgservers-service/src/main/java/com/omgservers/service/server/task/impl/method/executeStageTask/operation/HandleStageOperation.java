package com.omgservers.service.server.task.impl.method.executeStageTask.operation;

import com.omgservers.service.server.task.impl.method.executeStageTask.dto.FetchStageResult;
import com.omgservers.service.server.task.impl.method.executeStageTask.dto.HandleStageResult;

public interface HandleStageOperation {
    HandleStageResult execute(FetchStageResult fetchStageResult);
}
