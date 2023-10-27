package com.omgservers.module.script.impl.operation.mapScriptRequest;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.scriptRequest.ScriptRequestModel;
import com.omgservers.model.scriptRequest.ScriptRequestQualifierEnum;
import com.omgservers.module.script.impl.luaRequest.LuaRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class MapScriptRequestOperationImpl implements MapScriptRequestOperation {

    final Map<ScriptRequestQualifierEnum, ScriptRequestMapper> scriptEventMappers;

    MapScriptRequestOperationImpl(Instance<ScriptRequestMapper> scriptEventMappersBeans) {
        this.scriptEventMappers = new ConcurrentHashMap<>();
        scriptEventMappersBeans.stream().forEach(scriptEventMapper -> {
            final var qualifier = scriptEventMapper.getQualifier();
            if (scriptEventMappers.put(qualifier, scriptEventMapper) != null) {
                log.error("Multiple script event mappers were detected, qualifier={}", qualifier);
            }
        });
    }

    @Override
    public LuaRequest mapScriptRequest(ScriptRequestModel scriptRequest) {

        final var qualifier = scriptRequest.getQualifier();
        final var qualifierBodyClass = qualifier.getArgumentsClass();

        if (!qualifierBodyClass.isInstance(scriptRequest.getArguments())) {
            throw new ServerSideBadRequestException("Qualifier and message body are mismatch, " +
                    "scriptEvent=" + scriptRequest);
        }

        if (!scriptEventMappers.containsKey(qualifier)) {
            throw new ServerSideBadRequestException("Script event mapper was not found, scriptEvent=" + scriptRequest);
        }

        final var mapper = scriptEventMappers.get(qualifier);
        return mapper.map(scriptRequest);
    }
}
