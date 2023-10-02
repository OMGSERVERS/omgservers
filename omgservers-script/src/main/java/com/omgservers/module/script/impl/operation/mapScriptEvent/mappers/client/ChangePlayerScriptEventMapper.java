package com.omgservers.module.script.impl.operation.mapScriptEvent.mappers.client;

import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.ScriptEventQualifierEnum;
import com.omgservers.model.scriptEvent.body.ChangePlayerScriptEventBodyModel;
import com.omgservers.module.script.impl.event.LuaEvent;
import com.omgservers.module.script.impl.event.player.ChangePlayerLuaEvent;
import com.omgservers.module.script.impl.operation.coerceJavaObject.CoerceJavaObjectOperation;
import com.omgservers.module.script.impl.operation.mapScriptEvent.ScriptEventMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class ChangePlayerScriptEventMapper implements ScriptEventMapper {

    final CoerceJavaObjectOperation coerceJavaObjectOperation;

    @Override
    public ScriptEventQualifierEnum getQualifier() {
        return ScriptEventQualifierEnum.CHANGE_PLAYER;
    }

    @Override
    public LuaEvent map(ScriptEventModel scriptEvent) {
        final var body = (ChangePlayerScriptEventBodyModel) scriptEvent.getBody();
        final var luaData = coerceJavaObjectOperation.coerceJavaObject(body.getData());
        return ChangePlayerLuaEvent.builder()
                .userId(body.getUserId())
                .playerId(body.getPlayerId())
                .clientId(body.getClientId())
                .data(luaData)
                .build();
    }
}
