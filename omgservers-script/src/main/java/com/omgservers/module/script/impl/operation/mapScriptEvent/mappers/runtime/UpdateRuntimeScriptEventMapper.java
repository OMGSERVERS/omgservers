package com.omgservers.module.script.impl.operation.mapScriptEvent.mappers.runtime;

import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.ScriptEventQualifierEnum;
import com.omgservers.model.scriptEvent.body.UpdateRuntimeScriptEventBodyModel;
import com.omgservers.module.script.impl.event.LuaEvent;
import com.omgservers.module.script.impl.event.runtime.UpdateRuntimeLuaEvent;
import com.omgservers.module.script.impl.operation.mapScriptEvent.ScriptEventMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class UpdateRuntimeScriptEventMapper implements ScriptEventMapper {

    @Override
    public ScriptEventQualifierEnum getQualifier() {
        return ScriptEventQualifierEnum.UPDATE_RUNTIME;
    }

    @Override
    public LuaEvent map(ScriptEventModel scriptEvent) {
        final var body = (UpdateRuntimeScriptEventBodyModel) scriptEvent.getBody();
        return UpdateRuntimeLuaEvent.builder()
                .time(body.getTime())
                .build();
    }
}
