package com.omgservers.dispatcher.operation;

import io.smallrye.mutiny.Uni;

public interface Task<T> {
    Uni<Boolean> execute(T taskArguments);
}
