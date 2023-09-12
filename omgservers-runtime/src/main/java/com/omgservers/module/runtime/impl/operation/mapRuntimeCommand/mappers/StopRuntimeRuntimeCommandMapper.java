package com.omgservers.module.runtime.impl.operation.mapRuntimeCommand.mappers;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.StopRuntimeCommandBodyModel;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.body.StopScriptEventBodyModel;
import com.omgservers.module.runtime.impl.operation.mapRuntimeCommand.RuntimeCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StopRuntimeRuntimeCommandMapper implements RuntimeCommandMapper {

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.STOP_RUNTIME;
    }

    @Override
    public ScriptEventModel map(RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (StopRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var scriptEventBody = StopScriptEventBodyModel.builder()
                .build();
        return new ScriptEventModel(scriptEventBody.getQualifier(), scriptEventBody);
    }
}
