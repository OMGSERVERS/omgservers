package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.service.server.task.impl.method.executeDeploymentTask.component.MatchmakerAssigner;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;

public interface CreateMatchmakerAssignerOperation {
    MatchmakerAssigner execute(FetchDeploymentResult fetchDeploymentResult,
                               HandleDeploymentResult handleDeploymentResult);
}
