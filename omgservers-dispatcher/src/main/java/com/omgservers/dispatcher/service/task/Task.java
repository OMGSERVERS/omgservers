package com.omgservers.dispatcher.service.task;

import io.smallrye.mutiny.Uni;

public interface Task<T> {
    Uni<Boolean> execute(T taskArguments);
}
