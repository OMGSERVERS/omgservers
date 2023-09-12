package com.omgservers.module.script.impl.operation.mapScriptEvent;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import com.omgservers.model.scriptEvent.ScriptEventQualifierEnum;
import com.omgservers.module.script.impl.luaEvent.LuaEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class MapScriptEventOperationImpl implements MapScriptEventOperation {

    final Map<ScriptEventQualifierEnum, ScriptEventMapper> scriptEventMappers;

    MapScriptEventOperationImpl(Instance<ScriptEventMapper> scriptEventMappersBeans) {
        this.scriptEventMappers = new ConcurrentHashMap<>();
        scriptEventMappersBeans.stream().forEach(scriptEventMapper -> {
            final var qualifier = scriptEventMapper.getQualifier();
            if (scriptEventMappers.put(qualifier, scriptEventMapper) != null) {
                log.error("Multiple script event mappers were detected, qualifier={}", qualifier);
            } else {
                log.info("Script event mapper was added, qualifier={}, mapper={}",
                        qualifier, scriptEventMapper.getClass().getSimpleName());
            }
        });
    }

    @Override
    public LuaEvent mapScriptEvent(ScriptEventModel scriptEvent) {

        final var qualifier = scriptEvent.getQualifier();
        final var qualifierBodyClass = qualifier.getBodyClass();

        if (!qualifierBodyClass.isInstance(scriptEvent.getBody())) {
            throw new ServerSideBadRequestException("Qualifier and message body are mismatch, " +
                    "scriptEvent=" + scriptEvent);
        }

        if (!scriptEventMappers.containsKey(qualifier)) {
            throw new ServerSideBadRequestException("Script event mapper was not found, scriptEvent=" + scriptEvent);
        }

        final var mapper = scriptEventMappers.get(qualifier);
        return mapper.map(scriptEvent);
    }
}
