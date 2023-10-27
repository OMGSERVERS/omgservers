package com.omgservers.job.script.operation.mapRuntimeCommand;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.scriptRequest.ScriptRequestModel;

public interface MapRuntimeCommandOperation {
    ScriptRequestModel mapRuntimeCommand(RuntimeCommandModel runtimeCommand);
}
