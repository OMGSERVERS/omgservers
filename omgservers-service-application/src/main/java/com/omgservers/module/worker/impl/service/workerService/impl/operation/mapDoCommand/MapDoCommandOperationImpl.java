package com.omgservers.module.worker.impl.service.workerService.impl.operation.mapDoCommand;

import com.omgservers.exception.ServerSideBadRequestException;
import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.model.event.EventModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class MapDoCommandOperationImpl implements MapDoCommandOperation {

    final Map<DoCommandQualifierEnum, DoCommandMapper> doCommandMappers;

    MapDoCommandOperationImpl(Instance<DoCommandMapper> doCommandMappersBeans) {
        this.doCommandMappers = new ConcurrentHashMap<>();
        doCommandMappersBeans.stream().forEach(doCommandMapper -> {
            final var qualifier = doCommandMapper.getQualifier();
            if (doCommandMappers.put(qualifier, doCommandMapper) != null) {
                log.error("Multiple do command mappers were detected, qualifier={}", qualifier);
            }
        });
    }

    @Override
    public EventModel mapDoCommand(final Long runtimeId, final DoCommandModel doCommand) {
        final var qualifier = doCommand.getQualifier();
        final var qualifierBodyClass = qualifier.getBodyClass();

        if (!qualifierBodyClass.isInstance(doCommand.getBody())) {
            throw new ServerSideBadRequestException("Qualifier and do command body are mismatch, " +
                    "doCommand=" + doCommand);
        }

        if (!doCommandMappers.containsKey(qualifier)) {
            throw new ServerSideBadRequestException("Do command mapper was not found, doCommand=" + doCommand);
        }

        final var mapper = doCommandMappers.get(qualifier);
        return mapper.map(runtimeId, doCommand);
    }
}
