package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.executeDoCommand;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.event.EventModel;
import io.smallrye.mutiny.Uni;

public interface DoCommandExecutor {

    DoCommandQualifierEnum getQualifier();

    Uni<Void> execute(Long runtimeId, DoCommandModel doCommand);
}
