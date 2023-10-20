package com.omgservers.module.script.impl.operation.mapScriptRequest.mapper;

import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.model.scriptRequest.ScriptRequestQualifierEnum;
import com.omgservers.model.scriptRequest.arguments.ChangePlayerScriptRequestArgumentsModel;
import com.omgservers.module.script.impl.luaRequest.LuaRequest;
import com.omgservers.module.script.impl.luaRequest.ChangePlayerLuaRequest;
import com.omgservers.module.script.impl.operation.coerceJavaObject.CoerceJavaObjectOperation;
import com.omgservers.module.script.impl.operation.mapScriptRequest.ScriptRequestMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
        final var luaData = coerceJavaObjectOperation.coerceJavaObject(arguments.getData());
        return ChangePlayerLuaRequest.builder()
                .userId(arguments.getUserId())
                .playerId(arguments.getPlayerId())
                .clientId(arguments.getClientId())
                .data(luaData)
                .build();
    }
}
