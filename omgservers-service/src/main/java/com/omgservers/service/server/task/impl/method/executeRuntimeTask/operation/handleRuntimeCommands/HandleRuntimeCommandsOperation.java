package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation.handleRuntimeCommands;

import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.FetchRuntimeResult;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.HandleRuntimeResult;

public interface HandleRuntimeCommandsOperation {
    void execute(FetchRuntimeResult fetchRuntimeResult,
                 HandleRuntimeResult handleRuntimeResult);
}
