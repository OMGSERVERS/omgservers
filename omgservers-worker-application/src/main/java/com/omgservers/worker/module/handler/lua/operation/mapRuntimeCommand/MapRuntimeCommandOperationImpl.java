package com.omgservers.worker.module.handler.lua.operation.mapRuntimeCommand;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.worker.module.handler.lua.luaRequest.LuaRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class MapRuntimeCommandOperationImpl implements MapRuntimeCommandOperation {

    final Map<RuntimeCommandQualifierEnum, RuntimeCommandMapper> runtimeCommandMappers;

    MapRuntimeCommandOperationImpl(final Instance<RuntimeCommandMapper> runtimeCommandMappersBeans) {
        this.runtimeCommandMappers = new ConcurrentHashMap<>();
        runtimeCommandMappersBeans.stream().forEach(runtimeCommandMapper -> {
            final var qualifier = runtimeCommandMapper.getQualifier();
            if (runtimeCommandMappers.put(qualifier, runtimeCommandMapper) != null) {
                log.error("Multiple runtime command mappers were detected, qualifier={}", qualifier);
            }
        });
    }

    @Override
    public LuaRequest mapRuntimeCommand(final RuntimeCommandModel runtimeCommand) {
        final var qualifier = runtimeCommand.getQualifier();
        final var qualifierBodyClass = qualifier.getBodyClass();

        if (!qualifierBodyClass.isInstance(runtimeCommand.getBody())) {
            throw new IllegalArgumentException("Qualifier and message body are mismatch, " +
                    "runtimeCommand=" + runtimeCommand);
        }

        if (!runtimeCommandMappers.containsKey(qualifier)) {
            throw new IllegalArgumentException("Runtime command mapper was not found, " +
                    "runtimeCommand=" + runtimeCommand);
        }

        final var mapper = runtimeCommandMappers.get(qualifier);
        return mapper.map(runtimeCommand);
    }
}
