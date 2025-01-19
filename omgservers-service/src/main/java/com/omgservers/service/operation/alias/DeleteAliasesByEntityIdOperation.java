package com.omgservers.service.operation.alias;

import io.smallrye.mutiny.Uni;

public interface DeleteAliasesByEntityIdOperation {
    Uni<Void> execute(Long shardKey, Long entityId);
}
