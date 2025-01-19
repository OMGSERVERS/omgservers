package com.omgservers.service.operation.job;

import io.smallrye.mutiny.Uni;

public interface FindAndDeleteJobOperation {
    Uni<Void> execute(Long shardKey, Long entityId);
}
