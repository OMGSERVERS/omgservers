package com.omgservers.module.script.impl.operation.mapScriptEvent.mappers.client;

import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.ScriptEventQualifierEnum;
import com.omgservers.model.scriptEvent.body.SignedUpScriptEventBodyModel;
import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import com.omgservers.module.script.impl.luaEvent.player.SignedUpLuaEvent;
import com.omgservers.module.script.impl.operation.mapScriptEvent.ScriptEventMapper;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SignedUpScriptEventMapper implements ScriptEventMapper {

    @Override
    public ScriptEventQualifierEnum getQualifier() {
        return ScriptEventQualifierEnum.SIGNED_UP;
    }

    @Override
    public LuaEvent map(ScriptEventModel scriptEvent) {
        final var body = (SignedUpScriptEventBodyModel) scriptEvent.getBody();
        return SignedUpLuaEvent.builder()
                .userId(body.getUserId())
                .playerId(body.getPlayerId())
                .clientId(body.getClientId())
                .build();
    }
}
