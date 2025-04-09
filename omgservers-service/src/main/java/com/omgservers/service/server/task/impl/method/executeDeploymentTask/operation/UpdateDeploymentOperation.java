package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.HandleDeploymentResult;
import io.smallrye.mutiny.Uni;

public interface UpdateDeploymentOperation {
    Uni<Void> execute(HandleDeploymentResult handleDeploymentResult);
}
