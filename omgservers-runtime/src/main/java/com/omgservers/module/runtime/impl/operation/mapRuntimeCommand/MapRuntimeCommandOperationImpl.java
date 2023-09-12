package com.omgservers.module.runtime.impl.operation.mapRuntimeCommand;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.model.scriptEvent.ScriptEventModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class MapRuntimeCommandOperationImpl implements MapRuntimeCommandOperation {

    final Map<RuntimeCommandQualifierEnum, RuntimeCommandMapper> runtimeCommandMappers;

    MapRuntimeCommandOperationImpl(Instance<RuntimeCommandMapper> runtimeCommandMappersBeans) {
        this.runtimeCommandMappers = new ConcurrentHashMap<>();
        runtimeCommandMappersBeans.stream().forEach(runtimeCommandMapper -> {
            final var qualifier = runtimeCommandMapper.getQualifier();
            if (runtimeCommandMappers.put(qualifier, runtimeCommandMapper) != null) {
                log.error("Multiple runtime command mappers were detected, qualifier={}", qualifier);
            } else {
                log.info("Runtime command mapper was added, qualifier={}, mapper={}",
                        qualifier, runtimeCommandMapper.getClass().getSimpleName());
            }
        });
    }

    @Override
    public ScriptEventModel mapRuntimeCommand(RuntimeCommandModel runtimeCommand) {
        final var qualifier = runtimeCommand.getQualifier();
        final var qualifierBodyClass = qualifier.getBodyClass();

        if (!qualifierBodyClass.isInstance(runtimeCommand.getBody())) {
            throw new ServerSideBadRequestException("Qualifier and message body are mismatch, " +
                    "runtimeCommand=" + runtimeCommand);
        }

        if (!runtimeCommandMappers.containsKey(qualifier)) {
            throw new ServerSideBadRequestException("Runtime command mapper was not found, " +
                    "runtimeCommand=" + runtimeCommand);
        }

        final var mapper = runtimeCommandMappers.get(qualifier);
        return mapper.map(runtimeCommand);
    }
}
