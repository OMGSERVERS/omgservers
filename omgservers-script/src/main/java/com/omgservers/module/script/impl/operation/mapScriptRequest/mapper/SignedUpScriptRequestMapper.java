package com.omgservers.module.script.impl.operation.mapScriptRequest.mapper;

import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.model.scriptRequest.ScriptRequestQualifierEnum;
import com.omgservers.model.scriptRequest.arguments.SignedUpScriptRequestArgumentsModel;
import com.omgservers.module.script.impl.luaRequest.LuaRequest;
import com.omgservers.module.script.impl.luaRequest.SignedUpLuaRequest;
import com.omgservers.module.script.impl.operation.mapScriptRequest.ScriptRequestMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SignedUpScriptRequestMapper implements ScriptRequestMapper {

    @Override
    public ScriptRequestQualifierEnum getQualifier() {
        return ScriptRequestQualifierEnum.SIGNED_UP;
    }

    @Override
    public LuaRequest map(ScriptRequestModel scriptRequest) {
        final var body = (SignedUpScriptRequestArgumentsModel) scriptRequest.getArguments();
        return SignedUpLuaRequest.builder()
                .userId(body.getUserId())
                .playerId(body.getPlayerId())
                .clientId(body.getClientId())
                .build();
    }
}
