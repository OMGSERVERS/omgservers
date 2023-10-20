package com.omgservers.module.script.impl.operation.mapScriptRequest.mapper;

import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.model.scriptRequest.ScriptRequestQualifierEnum;
import com.omgservers.model.scriptRequest.arguments.InitRuntimeScriptRequestArgumentsModel;
import com.omgservers.module.script.impl.luaRequest.LuaRequest;
import com.omgservers.module.script.impl.luaRequest.InitRuntimeLuaRequest;
import com.omgservers.module.script.impl.operation.coerceJavaObject.CoerceJavaObjectOperation;
import com.omgservers.module.script.impl.operation.mapScriptRequest.ScriptRequestMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class InitRuntimeScriptRequestMapper implements ScriptRequestMapper {

    final CoerceJavaObjectOperation coerceJavaObjectOperation;

    @Override
    public ScriptRequestQualifierEnum getQualifier() {
        return ScriptRequestQualifierEnum.INIT_RUNTIME;
    }

    @Override
    public LuaRequest map(ScriptRequestModel scriptRequest) {
        final var body = (InitRuntimeScriptRequestArgumentsModel) scriptRequest.getArguments();
        final var luaConfig = coerceJavaObjectOperation.coerceJavaObject(body.getConfig());

        return InitRuntimeLuaRequest.builder()
                .config(luaConfig)
                .build();
    }
}
