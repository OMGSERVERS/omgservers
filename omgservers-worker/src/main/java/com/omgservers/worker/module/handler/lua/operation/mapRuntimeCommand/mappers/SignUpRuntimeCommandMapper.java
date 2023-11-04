package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.mappers;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.SignUpRuntimeCommandBodyModel;
import com.omgservers.worker.module.handler.lua.luaCommand.impl.SignUpLuaCommand;
import com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.RuntimeCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SignUpRuntimeCommandMapper implements RuntimeCommandMapper {

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.SIGN_UP;
    }

    @Override
    public SignUpLuaCommand map(final RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (SignUpRuntimeCommandBodyModel) runtimeCommand.getBody();
        return new SignUpLuaCommand(runtimeCommandBody.getUserId(), runtimeCommandBody.getClientId());
    }
}
