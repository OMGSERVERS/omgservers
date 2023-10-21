package com.omgservers.job.script.operation.mapRuntimeCommand.mappers;

import com.omgservers.job.script.operation.mapRuntimeCommand.RuntimeCommandMapper;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.AddClientRuntimeCommandBodyModel;
import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.model.scriptRequest.arguments.AddClientRequestArgumentsModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AddClientRuntimeCommandMapper implements RuntimeCommandMapper {

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.ADD_CLIENT;
    }

    @Override
    public ScriptRequestModel map(final RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (AddClientRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var scriptEventBody = AddClientRequestArgumentsModel.builder()
                .userId(runtimeCommandBody.getUserId())
                .clientId(runtimeCommandBody.getClientId())
                .build();
        return new ScriptRequestModel(scriptEventBody.getQualifier(), scriptEventBody);
    }
}
