package com.omgservers.service.operation.client;

import io.smallrye.mutiny.Uni;

public interface SelectClientRuntimeOperation {
    Uni<Long> execute(Long clientId);
}
