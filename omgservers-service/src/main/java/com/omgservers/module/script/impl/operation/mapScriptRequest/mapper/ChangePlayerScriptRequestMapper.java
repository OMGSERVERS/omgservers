package com.omgservers.module.script.impl.operation.mapScriptRequest.mapper;

import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.model.scriptRequest.ScriptRequestQualifierEnum;
import com.omgservers.model.scriptRequest.arguments.ChangePlayerScriptRequestArgumentsModel;
import com.omgservers.module.script.impl.luaRequest.ChangePlayerLuaRequest;
import com.omgservers.module.script.impl.luaRequest.LuaRequest;
import com.omgservers.module.script.impl.operation.coerceJavaObject.CoerceJavaObjectOperation;
import com.omgservers.module.script.impl.operation.mapScriptRequest.ScriptRequestMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.luaj.vm2.LuaBoolean;
import org.luaj.vm2.LuaTable;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ChangePlayerScriptRequestMapper implements ScriptRequestMapper {

    final CoerceJavaObjectOperation coerceJavaObjectOperation;

    @Override
    public ScriptRequestQualifierEnum getQualifier() {
        return ScriptRequestQualifierEnum.CHANGE_PLAYER;
    }

    @Override
    public LuaRequest map(ScriptRequestModel scriptRequest) {
        final var arguments = (ChangePlayerScriptRequestArgumentsModel) scriptRequest.getArguments();
        return ChangePlayerLuaRequest.builder()
                .userId(arguments.getUserId())
                .clientId(arguments.getClientId())
                .attributes(parseAttributes(arguments.getAttributes()))
                .object(coerceJavaObjectOperation.coerceJavaObject(arguments.getObject()))
                .message(coerceJavaObjectOperation.coerceJavaObject(arguments.getMessage()))
                .build();
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
