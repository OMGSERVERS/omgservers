package com.omgservers.job.script.operation.mapRuntimeCommand.mappers;

import com.omgservers.job.script.operation.mapRuntimeCommand.RuntimeCommandMapper;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyModel;
import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.model.scriptRequest.arguments.HandleMessageScriptRequestArgumentsModel;
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
    public ScriptRequestModel map(final RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (HandleMessageRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var scriptEventBody = HandleMessageScriptRequestArgumentsModel.builder()
                .userId(runtimeCommandBody.getUserId())
                .playerId(runtimeCommandBody.getPlayerId())
                .clientId(runtimeCommandBody.getClientId())
                .data(runtimeCommandBody.getData())
                .build();
        return new ScriptRequestModel(scriptEventBody.getQualifier(), scriptEventBody);
    }
}
