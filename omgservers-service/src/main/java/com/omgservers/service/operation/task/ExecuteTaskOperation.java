package com.omgservers.service.operation.task;

import com.omgservers.service.server.task.Task;
import io.smallrye.mutiny.Uni;

public interface ExecuteTaskOperation {
    <T> Uni<Boolean> executeFailSafe(Task<T> task, T arguments);
}
