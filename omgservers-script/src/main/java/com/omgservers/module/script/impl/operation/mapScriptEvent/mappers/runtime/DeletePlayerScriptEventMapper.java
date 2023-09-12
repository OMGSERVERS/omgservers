package com.omgservers.module.script.impl.operation.mapScriptEvent.mappers.runtime;

import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.ScriptEventQualifierEnum;
import com.omgservers.model.scriptEvent.body.DeletePlayerScriptEventBodyModel;
import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import com.omgservers.module.script.impl.luaEvent.runtime.DeletePlayerLuaEvent;
import com.omgservers.module.script.impl.operation.mapScriptEvent.ScriptEventMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class DeletePlayerScriptEventMapper implements ScriptEventMapper {

    @Override
    public ScriptEventQualifierEnum getQualifier() {
        return ScriptEventQualifierEnum.DELETE_PLAYER;
    }

    @Override
    public LuaEvent map(ScriptEventModel scriptEvent) {
        final var body = (DeletePlayerScriptEventBodyModel) scriptEvent.getBody();
        return DeletePlayerLuaEvent.builder()
                .userId(body.getUserId())
                .playerId(body.getPlayerId())
                .clientId(body.getClientId())
                .build();
    }
}
