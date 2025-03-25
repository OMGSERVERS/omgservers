package com.omgservers.service.service.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.service.service.task.impl.method.executeRuntimeTask.dto.FetchRuntimeResult;
import com.omgservers.service.service.task.impl.method.executeRuntimeTask.dto.HandleRuntimeResult;

public interface HandleRuntimeLastActivityOperation {
    void execute(FetchRuntimeResult fetchRuntimeResult,
                 HandleRuntimeResult handleRuntimeResult);
}
