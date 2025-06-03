package com.omgservers.dispatcher.operation;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ApplicationScoped
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class ExecuteTaskOperationImpl implements ExecuteTaskOperation {

    @Override
    public <T> Uni<Boolean> executeFailSafe(Task<T> task, T arguments) {
        return task.execute(arguments)
                .onFailure()
                .recoverWithUni(t -> {
                    log.error("Failed to execute task=\"{}\", {}:{}",
                            task.getClass().getSimpleName(),
                            t.getClass().getSimpleName(), t.getMessage());
                    return Uni.createFrom().item(Boolean.FALSE);
                })
                .invoke(result -> log.trace("Task finished, result={}", result));
    }
}
