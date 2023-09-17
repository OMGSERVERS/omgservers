package com.omgservers.job.runtime.operation.mapRuntimeCommand;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.scriptEvent.ScriptEventModel;

public interface RuntimeCommandMapper {

    RuntimeCommandQualifierEnum getQualifier();

    ScriptEventModel map(RuntimeCommandModel runtimeCommand);
}
