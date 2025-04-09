package com.omgservers.service.server.task.impl.method.executePoolTask.operation.handleDeploymentCommands;

import com.omgservers.service.server.task.impl.method.executePoolTask.dto.FetchPoolResult;
import com.omgservers.service.server.task.impl.method.executePoolTask.dto.HandlePoolResult;

public interface HandlePoolCommandsOperation {
    void execute(FetchPoolResult fetchPoolResult,
                 HandlePoolResult handlePoolResult);
}
