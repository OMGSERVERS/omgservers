package com.omgservers.job.script.operation.mapRuntimeCommand.mappers;

import com.omgservers.job.script.operation.mapRuntimeCommand.RuntimeCommandMapper;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.StopRuntimeCommandBodyModel;
import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.model.scriptRequest.arguments.StopRuntimeScriptRequestArgumentsModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StopRuntimeCommandMapper implements RuntimeCommandMapper {

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.STOP_RUNTIME;
    }

    @Override
    public ScriptRequestModel map(RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (StopRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var scriptEventBody = StopRuntimeScriptRequestArgumentsModel.builder()
                .build();
        return new ScriptRequestModel(scriptEventBody.getQualifier(), scriptEventBody);
    }
}
