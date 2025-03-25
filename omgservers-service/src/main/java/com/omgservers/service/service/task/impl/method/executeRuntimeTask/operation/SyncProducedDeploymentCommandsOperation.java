package com.omgservers.service.service.task.impl.method.executeRuntimeTask.operation;

import com.omgservers.schema.model.deploymentCommand.DeploymentCommandModel;
import io.smallrye.mutiny.Uni;

import java.util.List;

public interface SyncProducedDeploymentCommandsOperation {
    Uni<Void> execute(List<DeploymentCommandModel> producedDeploymentCommands);
}
