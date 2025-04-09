package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation.handleRuntimeCommands;

import com.omgservers.schema.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.schema.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.FetchRuntimeResult;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.HandleRuntimeResult;

public interface RuntimeCommandHandler {

    RuntimeCommandQualifierEnum getQualifier();

    boolean handle(FetchRuntimeResult fetchRuntimeResult,
                   HandleRuntimeResult handleRuntimeResult,
                   RuntimeCommandModel runtimeCommand);
}
