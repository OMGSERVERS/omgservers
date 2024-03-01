package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.mappers;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.AddMatchClientRuntimeCommandBodyModel;
import com.omgservers.worker.module.handler.lua.component.luaCommand.impl.AddClientLuaCommand;
import com.omgservers.worker.module.handler.lua.operation.coerceJavaObject.CoerceJavaObjectOperation;
import com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.RuntimeCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AddMatchClientRuntimeCommandMapper implements RuntimeCommandMapper {

    final CoerceJavaObjectOperation coerceJavaObjectOperation;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.ADD_MATCH_CLIENT;
    }

    @Override
    public AddClientLuaCommand map(final RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (AddMatchClientRuntimeCommandBodyModel) runtimeCommand.getBody();

        final var clientId = runtimeCommandBody.getClientId();
        final var groupName = runtimeCommandBody.getGroupName();
        final var luaAttributes = coerceJavaObjectOperation.coerceJavaObject(runtimeCommandBody.getAttributes());
        final var luaProfile = coerceJavaObjectOperation.coerceJavaObject(runtimeCommandBody.getProfile());

        return new AddClientLuaCommand(clientId,
                luaAttributes,
                luaProfile,
                groupName);
    }
}
