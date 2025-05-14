package com.omgservers.service.operation.entity;

import io.smallrye.mutiny.Uni;

public interface DeleteEntityOperation {

    Uni<Boolean> execute(Long entityId);

    Uni<Boolean> executeFailSafe(Long entityId);
}
