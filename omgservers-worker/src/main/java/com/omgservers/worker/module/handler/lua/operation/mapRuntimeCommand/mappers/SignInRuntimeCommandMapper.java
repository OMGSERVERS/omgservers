package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.mappers;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.SignInRuntimeCommandBodyModel;
import com.omgservers.worker.module.handler.lua.component.luaCommand.impl.SignInLuaCommand;
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
public class SignInRuntimeCommandMapper implements RuntimeCommandMapper {

    final CoerceJavaObjectOperation coerceJavaObjectOperation;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.SIGN_IN;
    }

    @Override
    public SignInLuaCommand map(LuaContext luaContext, final RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (SignInRuntimeCommandBodyModel) runtimeCommand.getBody();

        final var userId = runtimeCommandBody.getUserId();
        final var luaProfileOptional = luaContext.getProfile(userId);
        final var luaAttributesOptional = luaContext.getAttributes(userId);

        if (luaAttributesOptional.isPresent() && luaProfileOptional.isPresent()) {
            return new SignInLuaCommand(runtimeCommandBody.getUserId(),
                    runtimeCommandBody.getClientId(),
                    luaAttributesOptional.get(),
                    luaProfileOptional.get());
        } else {
            throw new IllegalArgumentException(
                    String.format("profiles or attributes were not found for runtime command, " +
                            "qualifier=%s, userId=%d", runtimeCommand.getQualifier(), userId));
        }
    }
}
