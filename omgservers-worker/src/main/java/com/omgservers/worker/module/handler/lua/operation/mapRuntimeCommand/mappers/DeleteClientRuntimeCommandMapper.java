package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.mappers;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.DeleteClientRuntimeCommandBodyModel;
import com.omgservers.worker.module.handler.lua.component.luaCommand.impl.DeleteClientLuaCommand;
import com.omgservers.worker.module.handler.lua.component.luaContext.LuaContext;
import com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.RuntimeCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DeleteClientRuntimeCommandMapper implements RuntimeCommandMapper {

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.DELETE_CLIENT;
    }

    @Override
    public DeleteClientLuaCommand map(LuaContext luaContext, final RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (DeleteClientRuntimeCommandBodyModel) runtimeCommand.getBody();

        final var clientId = runtimeCommandBody.getClientId();
        final var luaProfileOptional = luaContext.getProfile(clientId);
        final var luaAttributesOptional = luaContext.getAttributes(clientId);

        if (luaAttributesOptional.isPresent() && luaProfileOptional.isPresent()) {
            return new DeleteClientLuaCommand(clientId,
                    luaAttributesOptional.get(),
                    luaProfileOptional.get());
        } else {
            throw new IllegalArgumentException(
                    String.format("profiles or attributes were not found for runtime command, " +
                            "qualifier=%s, clientId=%d", runtimeCommand.getQualifier(), clientId));
        }
    }
}
