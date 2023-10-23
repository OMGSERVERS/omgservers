package com.omgservers.module.script.impl.operation.mapScriptRequest.mapper;

import com.omgservers.model.player.PlayerAttributesModel;
import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.model.scriptRequest.ScriptRequestQualifierEnum;
import com.omgservers.model.scriptRequest.arguments.SignInScriptRequestArgumentsModel;
import com.omgservers.module.script.impl.luaRequest.LuaRequest;
import com.omgservers.module.script.impl.luaRequest.SignInLuaRequest;
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
public class SignInScriptRequestMapper implements ScriptRequestMapper {

    final CoerceJavaObjectOperation coerceJavaObjectOperation;

    @Override
    public ScriptRequestQualifierEnum getQualifier() {
        return ScriptRequestQualifierEnum.SIGN_IN;
    }

    @Override
    public LuaRequest map(ScriptRequestModel scriptRequest) {
        final var body = (SignInScriptRequestArgumentsModel) scriptRequest.getArguments();
        return SignInLuaRequest.builder()
                .userId(body.getUserId())
                .clientId(body.getClientId())
                .attributes(parseAttributes(body.getAttributes()))
                .object(coerceJavaObjectOperation.coerceJavaObject(body.getObject()))
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
