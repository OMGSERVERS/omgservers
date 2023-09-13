package com.omgservers.module.script.impl.operation.mapScriptEvent.mappers.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.omgservers.exception.ServerSideBadRequestException;
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
import org.luaj.vm2.LuaValue;

import java.io.IOException;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class InitScriptEventMapper implements ScriptEventMapper {

    final ObjectMapper objectMapper;

    @Override
    public ScriptEventQualifierEnum getQualifier() {
        return ScriptEventQualifierEnum.INIT;
    }

    @Override
    public LuaEvent map(ScriptEventModel scriptEvent) {
        final var body = (InitScriptEventBodyModel) scriptEvent.getBody();

        try {
            // TODO: replace by coerce form luaj
            final var configString = objectMapper.writeValueAsString(body.getConfig());
            final var luaConfig = objectMapper.readValue(configString, LuaValue.class);

            return InitLuaEvent.builder()
                    .config(luaConfig)
                    .build();
        } catch (IOException e) {
            throw new ServerSideBadRequestException("config can't be parsed, scriptEvent=" + scriptEvent, e);
        }
    }
}
