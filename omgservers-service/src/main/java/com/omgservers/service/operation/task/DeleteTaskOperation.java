package com.omgservers.service.operation.task;

import io.smallrye.mutiny.Uni;

public interface DeleteTaskOperation {
    Uni<Boolean> execute(Long shardKey, Long entityId);

    Uni<Boolean> execute(Long entityId);
}
