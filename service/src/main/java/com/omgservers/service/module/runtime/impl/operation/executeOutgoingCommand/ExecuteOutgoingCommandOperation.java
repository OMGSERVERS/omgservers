package com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand;

import com.omgservers.schema.model.outgoingCommand.OutgoingCommandModel;
import io.smallrye.mutiny.Uni;

public interface ExecuteOutgoingCommandOperation {
    Uni<Void> executeOutgoingCommand(Long runtimeId, OutgoingCommandModel outgoingCommand);
}
