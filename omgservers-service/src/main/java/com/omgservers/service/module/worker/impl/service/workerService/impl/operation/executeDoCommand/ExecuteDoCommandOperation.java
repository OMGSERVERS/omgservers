package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.executeDoCommand;

import com.omgservers.model.doCommand.DoCommandModel;
import io.smallrye.mutiny.Uni;

public interface ExecuteDoCommandOperation {
    Uni<Void> executeDoCommand(Long runtimeId, DoCommandModel doCommand);
}
