package com.omgservers.job.runtime.operation.mapRuntimeCommand.mappers;

import com.omgservers.job.runtime.operation.mapRuntimeCommand.RuntimeCommandMapper;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.UpdateRuntimeCommandBodyModel;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.body.UpdateRuntimeScriptEventBodyModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UpdateRuntimeCommandMapper implements RuntimeCommandMapper {

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.UPDATE_RUNTIME;
    }

    @Override
    public ScriptEventModel map(RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (UpdateRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var scriptEventBody = UpdateRuntimeScriptEventBodyModel.builder()
                .time(runtimeCommandBody.getTime())
                .build();
        return new ScriptEventModel(scriptEventBody.getQualifier(), scriptEventBody);
    }
}
