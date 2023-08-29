package com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.impl;

import com.omgservers.model.runtimeCommand.RuntimeCommandModel;
import com.omgservers.model.runtimeCommand.RuntimeCommandQualifierEnum;
import com.omgservers.module.runtime.impl.operation.handleRuntimeCommand.HandleRuntimeCommandOperation;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@ApplicationScoped
class HandleRuntimeCommandOperationImpl implements HandleRuntimeCommandOperation {

    final Map<RuntimeCommandQualifierEnum, RuntimeCommandHandler> handlers;

    HandleRuntimeCommandOperationImpl(final Instance<RuntimeCommandHandler> beans) {
        handlers = new ConcurrentHashMap<>();
        beans.stream().forEach(runtimeCommandHandler -> {
            final var qualifier = runtimeCommandHandler.getQualifier();
            if (handlers.put(qualifier, runtimeCommandHandler) != null) {
                log.error("Multiple runtime command handlers were detected, qualifier={}", qualifier);
            } else {
                log.info("Runtime command handler was added, qualifier={}, handler={}",
                        qualifier, runtimeCommandHandler.getClass().getSimpleName());
            }
        });
    }


    @Override
    public Uni<Boolean> handleRuntimeCommand(RuntimeCommandModel runtimeCommand) {
        final var qualifier = runtimeCommand.getQualifier();
        if (handlers.containsKey(qualifier)) {
            final var handler = handlers.get(qualifier);
            final var body = runtimeCommand.getBody();
            if (qualifier.getBodyClass().isInstance(body)) {
                return handler.handleRuntimeCommand(runtimeCommand)
                        .invoke(result -> log.info("Runtime command was handled, " +
                                "result={}, runtimeCommand={}", result, runtimeCommand))
                        .onFailure()
                        .recoverWithItem(t -> {
                            log.error("Runtime command was failed, {}", t.getMessage());
                            return false;
                        });
            } else {
                log.error("Runtime command body has wrong type, runtimeCommand={}", runtimeCommand);
            }
        } else {
            log.error("Runtime command handler wasn't found, runtimeCommand={}", runtimeCommand);
        }
        return Uni.createFrom().item(false);
    }
}
