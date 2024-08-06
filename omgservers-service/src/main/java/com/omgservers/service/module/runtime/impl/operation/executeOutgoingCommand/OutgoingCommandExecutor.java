package com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand;

import com.omgservers.schema.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface OutgoingCommandExecutor {

    OutgoingCommandQualifierEnum getQualifier();

    Uni<Void> execute(Long runtimeId, OutgoingCommandModel outgoingCommand);
}
