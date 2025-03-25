package com.omgservers.service.operation.client;

import io.smallrye.mutiny.Uni;

public interface FindAndDeleteClientRuntimeRefOperation {
    Uni<Void> execute(Long clientId, Long runtimeId);
}
