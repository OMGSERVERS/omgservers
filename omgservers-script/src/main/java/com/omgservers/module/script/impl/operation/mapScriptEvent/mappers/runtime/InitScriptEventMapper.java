package com.omgservers.module.script.impl.operation.mapScriptEvent.mappers.runtime;

import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.ScriptEventQualifierEnum;
import com.omgservers.model.scriptEvent.body.InitScriptEventBodyModel;
import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import com.omgservers.module.script.impl.luaEvent.runtime.InitLuaEvent;
import com.omgservers.module.script.impl.operation.mapScriptEvent.ScriptEventMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class InitScriptEventMapper implements ScriptEventMapper {

    @Override
    public ScriptEventQualifierEnum getQualifier() {
        return ScriptEventQualifierEnum.INIT;
    }

    @Override
    public LuaEvent map(ScriptEventModel scriptEvent) {
        final var body = (InitScriptEventBodyModel) scriptEvent.getBody();
        return InitLuaEvent.builder()
                .build();
    }
}
