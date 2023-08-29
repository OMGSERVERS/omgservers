package com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.impl;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import io.smallrye.mutiny.Uni;

public interface RuntimeCommandHandler {
    RuntimeCommandQualifierEnum getQualifier();

    Uni<Boolean> handleRuntimeCommand(RuntimeCommandModel runtimeCommand);
}
