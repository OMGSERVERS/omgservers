package com.omgservers.job.script.operation.mapRuntimeCommand.mappers;

import com.omgservers.job.script.operation.mapRuntimeCommand.RuntimeCommandMapper;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.SignInRuntimeCommandBodyModel;
import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.model.scriptRequest.arguments.SignInScriptRequestArgumentsModel;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SignInRuntimeCommandMapper implements RuntimeCommandMapper {

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.SIGN_IN;
    }

    @Override
    public ScriptRequestModel map(final RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (SignInRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var scriptEventBody = SignInScriptRequestArgumentsModel.builder()
                .userId(runtimeCommandBody.getUserId())
                .clientId(runtimeCommandBody.getClientId())
                .build();
        return new ScriptRequestModel(scriptEventBody.getQualifier(), scriptEventBody);
    }
}
