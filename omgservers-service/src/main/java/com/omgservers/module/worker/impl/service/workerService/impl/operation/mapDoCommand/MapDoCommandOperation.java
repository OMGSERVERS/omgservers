package com.omgservers.module.worker.impl.service.workerService.impl.operation.mapDoCommand;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.event.EventModel;

public interface MapDoCommandOperation {
    EventModel mapDoCommand(Long runtimeId, DoCommandModel doCommand);
}
