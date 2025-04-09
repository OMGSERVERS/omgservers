package com.omgservers.service.server.task.impl.method.executePoolTask.operation.handleDeploymentCommands;

import com.omgservers.schema.model.poolCommand.PoolCommandQualifierEnum;
import com.omgservers.service.server.task.impl.method.executePoolTask.dto.FetchPoolResult;
import com.omgservers.service.server.task.impl.method.executePoolTask.dto.HandlePoolResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class HandlePoolCommandsOperationImpl implements HandlePoolCommandsOperation {

    final Map<PoolCommandQualifierEnum, PoolCommandHandler> poolCommandHandlers;

    HandlePoolCommandsOperationImpl(final Instance<PoolCommandHandler> poolCommandHandlerBeans) {
        this.poolCommandHandlers = new ConcurrentHashMap<>();
        poolCommandHandlerBeans.stream().forEach(poolCommandHandler -> {
            final var qualifier = poolCommandHandler.getQualifier();
            if (poolCommandHandlers.put(qualifier, poolCommandHandler) != null) {
                log.error("Multiple \"{}\" handlers were detected", qualifier);
            }
        });
    }

    @Override
    public void execute(final FetchPoolResult fetchPoolResult,
                        final HandlePoolResult handlePoolResult) {
        fetchPoolResult.poolState().getPoolCommands()
                .forEach(poolCommand -> {
                    final var qualifier = poolCommand.getQualifier();
                    final var qualifierBodyClass = qualifier.getBodyClass();
                    final var body = poolCommand.getBody();
                    final var commandId = poolCommand.getId();

                    if (!qualifierBodyClass.isInstance(body)) {
                        log.error("Qualifier \"{}\" and body class \"{}\" do not match, id={}",
                                qualifier, body.getClass().getSimpleName(), commandId);
                        return;
                    }

                    if (!poolCommandHandlers.containsKey(qualifier)) {
                        log.error("Handler for \"{}\" was not found, id={}",
                                qualifier, commandId);
                        return;
                    }

                    final var handled = poolCommandHandlers.get(qualifier)
                            .handle(fetchPoolResult, handlePoolResult, poolCommand);

                    if (handled) {
                        handlePoolResult.poolChangeOfState().getPoolCommandsToDelete()
                                .add(poolCommand.getId());
                    }
                });
    }
}
