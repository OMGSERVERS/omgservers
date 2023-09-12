package com.omgservers.module.script.impl.operation.mapScriptEvent.mappers.runtime;

import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.ScriptEventQualifierEnum;
import com.omgservers.model.scriptEvent.body.StopScriptEventBodyModel;
import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import com.omgservers.module.script.impl.luaEvent.runtime.StopLuaEvent;
import com.omgservers.module.script.impl.operation.mapScriptEvent.ScriptEventMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class StopScriptEventMapper implements ScriptEventMapper {

    @Override
    public ScriptEventQualifierEnum getQualifier() {
        return ScriptEventQualifierEnum.STOP;
    }

    @Override
    public LuaEvent map(ScriptEventModel scriptEvent) {
        final var body = (StopScriptEventBodyModel) scriptEvent.getBody();
        return StopLuaEvent.builder()
                .build();
    }
}
