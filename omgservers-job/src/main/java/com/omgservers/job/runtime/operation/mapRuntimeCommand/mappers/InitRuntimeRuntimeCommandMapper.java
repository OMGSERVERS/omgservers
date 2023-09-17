package com.omgservers.job.runtime.operation.mapRuntimeCommand.mappers;

import com.omgservers.job.runtime.operation.mapRuntimeCommand.RuntimeCommandMapper;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.InitRuntimeCommandBodyModel;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.body.InitScriptEventBodyModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class InitRuntimeRuntimeCommandMapper implements RuntimeCommandMapper {

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.INIT_RUNTIME;
    }

    @Override
    public ScriptEventModel map(final RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (InitRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var scriptEventBody = InitScriptEventBodyModel.builder()
                .config(runtimeCommandBody.getConfig())
                .build();
        return new ScriptEventModel(scriptEventBody.getQualifier(), scriptEventBody);
    }
}
