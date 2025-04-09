package com.omgservers.service.server.task.impl.method.executeRuntimeTask.operation.handleRuntimeCommands;

import com.omgservers.schema.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.FetchRuntimeResult;
import com.omgservers.service.server.task.impl.method.executeRuntimeTask.dto.HandleRuntimeResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class HandleRuntimeCommandsOperationImpl implements HandleRuntimeCommandsOperation {

    final Map<RuntimeCommandQualifierEnum, RuntimeCommandHandler> runtimeCommandHandlers;

    HandleRuntimeCommandsOperationImpl(final Instance<RuntimeCommandHandler> runtimeCommandHandlerBeans) {
        this.runtimeCommandHandlers = new ConcurrentHashMap<>();
        runtimeCommandHandlerBeans.stream().forEach(runtimeCommandHandler -> {
            final var qualifier = runtimeCommandHandler.getQualifier();
            if (runtimeCommandHandlers.put(qualifier, runtimeCommandHandler) != null) {
                log.error("Multiple \"{}\" handlers were detected", qualifier);
            }
        });
    }

    @Override
    public void execute(final FetchRuntimeResult fetchRuntimeResult,
                        final HandleRuntimeResult handleRuntimeResult) {
        fetchRuntimeResult.runtimeState().getRuntimeCommands()
                .forEach(runtimeCommand -> {
                    final var qualifier = runtimeCommand.getQualifier();
                    final var qualifierBodyClass = qualifier.getBodyClass();
                    final var body = runtimeCommand.getBody();
                    final var commandId = runtimeCommand.getId();

                    if (!qualifierBodyClass.isInstance(body)) {
                        log.error("Qualifier \"{}\" and body class \"{}\" do not match, id={}",
                                qualifier, body.getClass().getSimpleName(), commandId);
                        return;
                    }

                    if (!runtimeCommandHandlers.containsKey(qualifier)) {
                        log.error("Handler for \"{}\" was not found, id={}",
                                qualifier, commandId);
                        return;
                    }

                    final var handled = runtimeCommandHandlers.get(qualifier)
                            .handle(fetchRuntimeResult, handleRuntimeResult, runtimeCommand);

                    if (handled) {
                        handleRuntimeResult.runtimeChangeOfState().getRuntimeCommandsToDelete()
                                .add(runtimeCommand.getId());
                    }
                });
    }
}
