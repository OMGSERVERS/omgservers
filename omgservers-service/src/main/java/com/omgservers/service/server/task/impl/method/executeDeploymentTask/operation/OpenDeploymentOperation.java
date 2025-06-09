package com.omgservers.service.server.task.impl.method.executeDeploymentTask.operation;

import com.omgservers.service.server.task.impl.method.executeDeploymentTask.dto.FetchDeploymentResult;
import com.omgservers.service.server.task.impl.method.executeMatchmakerTask.dto.FetchMatchmakerResult;
import io.smallrye.mutiny.Uni;

public interface OpenDeploymentOperation {
    Uni<Void> execute(FetchDeploymentResult fetchDeploymentResult);
}
