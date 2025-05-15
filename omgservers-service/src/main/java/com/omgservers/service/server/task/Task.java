package com.omgservers.service.server.task;

import io.smallrye.mutiny.Uni;

public interface Task<T> {
    Uni<TaskResult> execute(T taskArguments);
}
