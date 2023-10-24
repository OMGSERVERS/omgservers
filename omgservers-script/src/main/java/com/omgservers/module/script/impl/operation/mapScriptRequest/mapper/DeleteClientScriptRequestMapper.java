package com.omgservers.module.script.impl.operation.mapScriptRequest.mapper;

import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.model.scriptRequest.ScriptRequestQualifierEnum;
import com.omgservers.model.scriptRequest.arguments.DeleteClientScriptRequestArgumentsModel;
import com.omgservers.module.script.impl.luaRequest.DeleteClientLuaRequest;
import com.omgservers.module.script.impl.luaRequest.LuaRequest;
import com.omgservers.module.script.impl.operation.mapScriptRequest.ScriptRequestMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DeleteClientScriptRequestMapper implements ScriptRequestMapper {

    @Override
    public ScriptRequestQualifierEnum getQualifier() {
        return ScriptRequestQualifierEnum.DELETE_CLIENT;
    }

    @Override
    public LuaRequest map(ScriptRequestModel scriptRequest) {
        final var body = (DeleteClientScriptRequestArgumentsModel) scriptRequest.getArguments();
        return DeleteClientLuaRequest.builder()
                .userId(body.getUserId())
                .clientId(body.getClientId())
                .build();
    }
}
