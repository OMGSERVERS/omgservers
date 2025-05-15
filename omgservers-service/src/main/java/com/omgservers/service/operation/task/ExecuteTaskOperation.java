package com.omgservers.service.operation.task;

import com.omgservers.service.server.task.Task;
import com.omgservers.service.server.task.TaskResult;
import io.smallrye.mutiny.Uni;

public interface ExecuteTaskOperation {
    <T> Uni<TaskResult> executeFailSafe(Task<T> task, T arguments);
}
