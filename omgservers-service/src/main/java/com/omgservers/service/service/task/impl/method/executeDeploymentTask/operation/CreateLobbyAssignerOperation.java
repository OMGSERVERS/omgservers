package com.omgservers.service.service.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.service.service.task.impl.method.executeDeploymentTask.component.LobbyAssigner;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.service.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;

public interface CreateLobbyAssignerOperation {
    LobbyAssigner execute(FetchDeploymentResult fetchDeploymentResult,
                          HandleDeploymentResult handleDeploymentResult);
}
