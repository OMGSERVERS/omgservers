package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.FetchRuntimeResult;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.HandleRuntimeResult;

public interface HandleRuntimeOperation {
    HandleRuntimeResult execute(FetchRuntimeResult fetchRuntimeResult);
}
