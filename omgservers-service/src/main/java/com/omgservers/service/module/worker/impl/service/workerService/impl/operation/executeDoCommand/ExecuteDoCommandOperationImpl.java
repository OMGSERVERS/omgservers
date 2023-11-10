package com.omgservers.service.module.worker.impl.service.workerService.impl.operation.executeDoCommand;

import com.omgservers.model.doCommand.DoCommandModel;
import com.omgservers.model.doCommand.DoCommandQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class ExecuteDoCommandOperationImpl implements ExecuteDoCommandOperation {

    final Map<DoCommandQualifierEnum, DoCommandExecutor> doCommandExecutors;

    ExecuteDoCommandOperationImpl(Instance<DoCommandExecutor> doCommandExecutorBeans) {
        this.doCommandExecutors = new ConcurrentHashMap<>();
        doCommandExecutorBeans.stream().forEach(doCommandExecutor -> {
            final var qualifier = doCommandExecutor.getQualifier();
            if (doCommandExecutors.put(qualifier, doCommandExecutor) != null) {
                log.error("Multiple do command executors were detected, qualifier={}", qualifier);
            }
        });
    }

    @Override
    public Uni<Void> executeDoCommand(final Long runtimeId, final DoCommandModel doCommand) {
        final var qualifier = doCommand.getQualifier();
        final var qualifierBodyClass = qualifier.getBodyClass();

        if (!qualifierBodyClass.isInstance(doCommand.getBody())) {
            throw new ServerSideBadRequestException("qualifier and do command body are mismatch, " +
                    "doCommand=" + doCommand);
        }

        if (!doCommandExecutors.containsKey(qualifier)) {
            throw new ServerSideBadRequestException("do command executor was not found, qualifiers=" + qualifier);
        }

        final var executor = doCommandExecutors.get(qualifier);
        return executor.execute(runtimeId, doCommand);
    }
}
