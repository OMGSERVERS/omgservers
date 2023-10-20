package com.omgservers.module.script.impl.operation.mapScriptRequest.mapper;

import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.model.scriptRequest.ScriptRequestQualifierEnum;
import com.omgservers.model.scriptRequest.arguments.StopRuntimeScriptRequestArgumentsModel;
import com.omgservers.module.script.impl.luaRequest.LuaRequest;
import com.omgservers.module.script.impl.luaRequest.StopRuntimeLuaRequest;
import com.omgservers.module.script.impl.operation.mapScriptRequest.ScriptRequestMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StopRuntimeScriptRequestMapper implements ScriptRequestMapper {

    @Override
    public ScriptRequestQualifierEnum getQualifier() {
        return ScriptRequestQualifierEnum.STOP_RUNTIME;
    }

    @Override
    public LuaRequest map(ScriptRequestModel scriptRequest) {
        final var body = (StopRuntimeScriptRequestArgumentsModel) scriptRequest.getArguments();
        return StopRuntimeLuaRequest.builder()
                .build();
    }
}
