package com.omgservers.job.runtime.operation.mapRuntimeCommand;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.scriptEvent.ScriptEventModel;

public interface MapRuntimeCommandOperation {
    ScriptEventModel mapRuntimeCommand(RuntimeCommandModel runtimeCommand);
}
