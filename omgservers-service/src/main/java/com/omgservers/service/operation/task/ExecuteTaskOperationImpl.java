package com.omgservers.service.operation.task;

import com.omgservers.service.server.task.Task;
import com.omgservers.service.server.task.TaskResult;
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
    public <T> Uni<TaskResult> executeFailSafe(Task<T> task, T arguments) {
        return task.execute(arguments)
                .onFailure()
                .recoverWithUni(t -> {
                    log.error("Failed to execute task=\"{}\", {}:{}",
                            task.getClass().getSimpleName(),
                            t.getClass().getSimpleName(), t.getMessage());
                    return Uni.createFrom().item(TaskResult.FAIL);
                })
                .invoke(result -> log.trace("Task finished, result={}", result));
    }
}
