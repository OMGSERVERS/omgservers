package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.mappers;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.HandleMessageRuntimeCommandBodyModel;
import com.omgservers.worker.module.handler.lua.component.luaCommand.impl.HandleMessageLuaCommand;
import com.omgservers.worker.module.handler.lua.component.luaContext.LuaContext;
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

        final var clientId = runtimeCommandBody.getClientId();
        final var luaProfileOptional = luaContext.getProfile(clientId);
        final var luaAttributesOptional = luaContext.getAttributes(clientId);

        if (luaAttributesOptional.isPresent() && luaProfileOptional.isPresent()) {
            final var message = runtimeCommandBody.getMessage();
            final var luaMessage = coerceJavaObjectOperation.coerceJavaObject(message);
            return new HandleMessageLuaCommand(clientId,
                    luaAttributesOptional.get(),
                    luaProfileOptional.get(),
                    luaMessage);
        } else {
            throw new IllegalArgumentException(
                    String.format("profiles or attributes were not found for runtime command, " +
                            "qualifier=%s, clientId=%d", runtimeCommand.getQualifier(), clientId));
        }
    }
}
