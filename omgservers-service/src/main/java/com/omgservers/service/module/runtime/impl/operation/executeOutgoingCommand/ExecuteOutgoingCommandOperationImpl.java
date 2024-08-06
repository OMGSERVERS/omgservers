package com.omgservers.service.module.runtime.impl.operation.executeOutgoingCommand;

import com.omgservers.schema.model.outgoingCommand.OutgoingCommandModel;
import com.omgservers.schema.model.outgoingCommand.OutgoingCommandQualifierEnum;
import com.omgservers.schema.model.exception.ExceptionQualifierEnum;
import com.omgservers.service.exception.ServerSideBadRequestException;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class ExecuteOutgoingCommandOperationImpl implements ExecuteOutgoingCommandOperation {

    final Map<OutgoingCommandQualifierEnum, OutgoingCommandExecutor> outgoingCommandExecutors;

    ExecuteOutgoingCommandOperationImpl(Instance<OutgoingCommandExecutor> outgoingCommandExecutorBeans) {
        this.outgoingCommandExecutors = new ConcurrentHashMap<>();
        outgoingCommandExecutorBeans.stream().forEach(outgoingCommandExecutor -> {
            final var qualifier = outgoingCommandExecutor.getQualifier();
            if (outgoingCommandExecutors.put(qualifier, outgoingCommandExecutor) != null) {
                log.error("Multiple outgoing command executors were detected, qualifier={}", qualifier);
            }
        });
    }

    @Override
    public Uni<Void> executeOutgoingCommand(final Long runtimeId, final OutgoingCommandModel outgoingCommand) {
        final var qualifier = outgoingCommand.getQualifier();
        final var qualifierBodyClass = qualifier.getBodyClass();

        if (!qualifierBodyClass.isInstance(outgoingCommand.getBody())) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.COMMAND_BODY_TYPE_MISMATCHED,
                    "qualifier and outgoing command body are mismatch, outgoingCommand=" + outgoingCommand);
        }

        if (!outgoingCommandExecutors.containsKey(qualifier)) {
            throw new ServerSideBadRequestException(ExceptionQualifierEnum.COMMAND_EXECUTOR_NOT_FOUND,
                    "outgoing command executor was not found, qualifiers=" + qualifier);
        }

        final var executor = outgoingCommandExecutors.get(qualifier);
        return executor.execute(runtimeId, outgoingCommand);
    }
}
