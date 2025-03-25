package com.omgservers.service.service.task.impl.method.executePoolTask.operation.handleDeploymentCommands;

import com.omgservers.schema.model.poolCommand.PoolCommandModel;
import com.omgservers.schema.model.poolCommand.PoolCommandQualifierEnum;
import com.omgservers.service.service.task.impl.method.executePoolTask.dto.FetchPoolResult;
import com.omgservers.service.service.task.impl.method.executePoolTask.dto.HandlePoolResult;

public interface PoolCommandHandler {

    PoolCommandQualifierEnum getQualifier();

    boolean handle(FetchPoolResult fetchPoolResult,
                   HandlePoolResult handlePoolResult,
                   PoolCommandModel poolCommand);
}
