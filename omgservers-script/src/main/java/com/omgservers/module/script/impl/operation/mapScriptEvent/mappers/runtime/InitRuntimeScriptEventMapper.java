package com.omgservers.module.script.impl.operation.mapScriptEvent.mappers.runtime;

import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.ScriptEventQualifierEnum;
import com.omgservers.model.scriptEvent.body.InitRuntimeScriptEventBodyModel;
import com.omgservers.module.script.impl.event.LuaEvent;
import com.omgservers.module.script.impl.event.runtime.InitRuntimeLuaEvent;
import com.omgservers.module.script.impl.operation.coerceJavaObject.CoerceJavaObjectOperation;
import com.omgservers.module.script.impl.operation.mapScriptEvent.ScriptEventMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class InitRuntimeScriptEventMapper implements ScriptEventMapper {

    final CoerceJavaObjectOperation coerceJavaObjectOperation;

    @Override
    public ScriptEventQualifierEnum getQualifier() {
        return ScriptEventQualifierEnum.INIT_RUNTIME;
    }

    @Override
    public LuaEvent map(ScriptEventModel scriptEvent) {
        final var body = (InitRuntimeScriptEventBodyModel) scriptEvent.getBody();
        final var luaConfig = coerceJavaObjectOperation.coerceJavaObject(body.getConfig());

        return InitRuntimeLuaEvent.builder()
                .config(luaConfig)
                .build();
    }
}
