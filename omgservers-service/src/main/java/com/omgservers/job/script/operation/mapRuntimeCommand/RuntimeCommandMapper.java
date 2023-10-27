package com.omgservers.job.script.operation.mapRuntimeCommand;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.scriptRequest.ScriptRequestModel;

public interface RuntimeCommandMapper {

    RuntimeCommandQualifierEnum getQualifier();

    ScriptRequestModel map(RuntimeCommandModel runtimeCommand);
}
