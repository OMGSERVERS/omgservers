package com.omgservers.module.script.impl.operation.mapScriptRequest.mapper;

import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.model.scriptRequest.ScriptRequestQualifierEnum;
import com.omgservers.model.scriptRequest.arguments.HandleMessageScriptRequestArgumentsModel;
import com.omgservers.module.script.impl.luaRequest.LuaRequest;
import com.omgservers.module.script.impl.luaRequest.HandleMessageLuaRequest;
import com.omgservers.module.script.impl.operation.coerceJavaObject.CoerceJavaObjectOperation;
import com.omgservers.module.script.impl.operation.mapScriptRequest.ScriptRequestMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class HandleMessageScriptRequestMapper implements ScriptRequestMapper {

    final CoerceJavaObjectOperation coerceJavaObjectOperation;

    @Override
    public ScriptRequestQualifierEnum getQualifier() {
        return ScriptRequestQualifierEnum.HANDLE_MESSAGE;
    }

    @Override
    public LuaRequest map(ScriptRequestModel scriptRequest) {
        final var body = (HandleMessageScriptRequestArgumentsModel) scriptRequest.getArguments();
        final var luaData = coerceJavaObjectOperation.coerceJavaObject(body.getData());
        return HandleMessageLuaRequest.builder()
                .userId(body.getUserId())
                .playerId(body.getPlayerId())
                .clientId(body.getClientId())
                .data(luaData)
                .build();
    }
}
