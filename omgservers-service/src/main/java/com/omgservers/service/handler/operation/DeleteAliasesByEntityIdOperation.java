package com.omgservers.service.handler.operation;

import io.smallrye.mutiny.Uni;

public interface DeleteAliasesByEntityIdOperation {
    Uni<Void> execute(Long shardKey, Long entityId);
}
