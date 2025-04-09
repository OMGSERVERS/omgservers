package com.omgservers.service.server.task;

import io.smallrye.mutiny.Uni;

public interface Task<T> {
    Uni<Boolean> execute(T taskArguments);
}
