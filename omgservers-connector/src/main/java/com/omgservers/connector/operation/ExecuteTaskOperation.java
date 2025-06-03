package com.omgservers.connector.operation;

import io.smallrye.mutiny.Uni;

public interface ExecuteTaskOperation {
    <T> Uni<Boolean> executeFailSafe(Task<T> task, T arguments);
}
