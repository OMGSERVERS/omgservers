package com.omgservers.job.runtime.operation.mapRuntimeCommand.mappers;

import com.omgservers.job.runtime.operation.mapRuntimeCommand.RuntimeCommandMapper;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyModel;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.body.HandleMessageScriptEventBodyModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class HandleMessageRuntimeRuntimeCommandMapper implements RuntimeCommandMapper {

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.HANDLE_MESSAGE;
    }

    @Override
    public ScriptEventModel map(final RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (HandleMessageRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var scriptEventBody = HandleMessageScriptEventBodyModel.builder()
                .userId(runtimeCommandBody.getUserId())
                .playerId(runtimeCommandBody.getPlayerId())
                .clientId(runtimeCommandBody.getClientId())
                .data(runtimeCommandBody.getData())
                .build();
        return new ScriptEventModel(scriptEventBody.getQualifier(), scriptEventBody);
    }
}
