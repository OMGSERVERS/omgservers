package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.mapDoCommand;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.event.EventModel;

public interface DoCommandMapper {

    DoCommandQualifierEnum getQualifier();

    EventModel map(Long runtimeId, DoCommandModel doCommand);
}
