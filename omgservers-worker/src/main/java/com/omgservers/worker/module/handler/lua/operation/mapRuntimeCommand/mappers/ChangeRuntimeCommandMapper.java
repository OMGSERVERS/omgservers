package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.mappers;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.ChangePlayerRuntimeCommandBodyModel;
import com.omgservers.worker.module.handler.lua.component.luaCommand.impl.ChangePlayerLuaCommand;
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
public class ChangeRuntimeCommandMapper implements RuntimeCommandMapper {

    final CoerceJavaObjectOperation coerceJavaObjectOperation;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.CHANGE_PLAYER;
    }

    @Override
    public ChangePlayerLuaCommand map(LuaContext luaContext, final RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (ChangePlayerRuntimeCommandBodyModel) runtimeCommand.getBody();

        final var userId = runtimeCommandBody.getUserId();
        final var luaProfileOptional = luaContext.getProfile(userId);
        final var luaAttributesOptional = luaContext.getAttributes(userId);

        if (luaAttributesOptional.isPresent() && luaProfileOptional.isPresent()) {
            return new ChangePlayerLuaCommand(
                    runtimeCommandBody.getUserId(),
                    runtimeCommandBody.getClientId(),
                    luaAttributesOptional.get(),
                    luaProfileOptional.get(),
                    coerceJavaObjectOperation.coerceJavaObject(runtimeCommandBody.getMessage()));
        } else {
            throw new IllegalArgumentException(
                    String.format("profiles or attributes were not found for runtime command, " +
                            "qualifier=%s, userId=%d", runtimeCommand.getQualifier(), userId));
        }
    }
}
