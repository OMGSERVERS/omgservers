package com.omgservers.job.runtime.operation.mapRuntimeCommand.mappers;

import com.omgservers.job.runtime.operation.mapRuntimeCommand.RuntimeCommandMapper;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.UpdateRuntimeCommandBodyModel;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.body.UpdateScriptEventBodyModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UpdateRuntimeRuntimeCommandMapper implements RuntimeCommandMapper {

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.UPDATE_RUNTIME;
    }

    @Override
    public ScriptEventModel map(RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (UpdateRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var scriptEventBody = UpdateScriptEventBodyModel.builder()
                .step(runtimeCommandBody.getStep())
                .build();
        return new ScriptEventModel(scriptEventBody.getQualifier(), scriptEventBody);
    }
}
