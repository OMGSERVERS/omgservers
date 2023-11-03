package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.mappers;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyModel;
import com.omgservers.worker.module.handler.lua.luaRequest.impl.HandleMessageLuaRequest;
import com.omgservers.worker.module.handler.lua.operation.coerceJavaObject.CoerceJavaObjectOperation;
import com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.RuntimeCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class HandleMessageRuntimeRuntimeCommandMapper implements RuntimeCommandMapper {

    final CoerceJavaObjectOperation coerceJavaObjectOperation;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.HANDLE_MESSAGE;
    }

    @Override
    public HandleMessageLuaRequest map(final RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (HandleMessageRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var luaMessage = coerceJavaObjectOperation.coerceJavaObject(runtimeCommandBody.getMessage());
        return new HandleMessageLuaRequest(runtimeCommandBody.getUserId(),
                runtimeCommandBody.getClientId(),
                luaMessage);
    }
}
