package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.mappers;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.UpdateRuntimeCommandBodyModel;
import com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.RuntimeCommandMapper;
import com.omgservers.worker.module.handler.lua.luaRequest.impl.UpdateRuntimeLuaRequest;
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
    public UpdateRuntimeLuaRequest map(RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (UpdateRuntimeCommandBodyModel) runtimeCommand.getBody();
        return new UpdateRuntimeLuaRequest(runtimeCommandBody.getTime());
    }
}
