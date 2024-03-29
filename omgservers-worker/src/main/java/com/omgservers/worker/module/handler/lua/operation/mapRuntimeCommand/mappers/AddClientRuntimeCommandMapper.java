package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.mappers;

import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.runtimeCommand.body.AddClientRuntimeCommandBodyModel;
import com.omgservers.worker.module.handler.lua.component.luaCommand.impl.AddClientLuaCommand;
import com.omgservers.worker.module.handler.lua.operation.coerceJavaObject.CoerceJavaObjectOperation;
import com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand.RuntimeCommandMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class AddClientRuntimeCommandMapper implements RuntimeCommandMapper {

    final CoerceJavaObjectOperation coerceJavaObjectOperation;

    @Override
    public RuntimeCommandQualifierEnum getQualifier() {
        return RuntimeCommandQualifierEnum.ADD_CLIENT;
    }

    @Override
    public AddClientLuaCommand map(final RuntimeCommandModel runtimeCommand) {
        final var runtimeCommandBody = (AddClientRuntimeCommandBodyModel) runtimeCommand.getBody();

        final var clientId = runtimeCommandBody.getClientId();

        final var luaAttributes = parseAttributes(runtimeCommandBody.getAttributes());
        final var luaProfile = coerceJavaObjectOperation.coerceJavaObject(runtimeCommandBody.getProfile());

        return new AddClientLuaCommand(clientId, luaAttributes, luaProfile);
    }

    LuaTable parseAttributes(PlayerAttributesModel attributes) {
        final var luaAttributes = new LuaTable();
        attributes.getAttributes().forEach(attribute -> {
            final var name = attribute.getName();
            final var value = attribute.getValue();
            switch (attribute.getType()) {
                case LONG -> luaAttributes.set(name, Long.parseLong(value));
                case DOUBLE -> luaAttributes.set(name, Double.parseDouble(value));
                case STRING -> luaAttributes.set(name, value);
                case BOOLEAN -> luaAttributes.set(name, LuaBoolean.valueOf(Boolean.parseBoolean(value)));
            }
        });

        return luaAttributes;
    }
}
