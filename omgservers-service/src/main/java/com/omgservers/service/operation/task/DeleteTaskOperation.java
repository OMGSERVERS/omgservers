package com.omgservers.service.operation.task;

import io.smallrye.mutiny.Uni;

public interface DeleteTaskOperation {
    Uni<Void> execute(Long shardKey, Long entityId);

    Uni<Void> execute(Long entityId);
}
