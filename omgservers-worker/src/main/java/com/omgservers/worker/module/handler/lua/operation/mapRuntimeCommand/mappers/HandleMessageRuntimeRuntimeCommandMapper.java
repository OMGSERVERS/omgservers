package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.mappers;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyModel;
import com.omgservers.worker.module.handler.lua.component.luaContext.LuaContext;
import com.omgservers.worker.module.handler.lua.component.luaCommand.impl.HandleMessageLuaCommand;
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
    public HandleMessageLuaCommand map(LuaContext luaContext, final RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (HandleMessageRuntimeCommandBodyModel) runtimeCommand.getBody();
        final var luaMessage = coerceJavaObjectOperation.coerceJavaObject(runtimeCommandBody.getMessage());
        return new HandleMessageLuaCommand(runtimeCommandBody.getUserId(),
                runtimeCommandBody.getClientId(),
                luaMessage);
    }
}
