package com.omgservers.service.operation.job;

import io.smallrye.mutiny.Uni;

public interface ExecuteTaskOperation {
    Uni<Boolean> execute(Uni<Boolean> task);
}
