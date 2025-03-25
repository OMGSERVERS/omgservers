package com.omgservers.service.operation.client;

import io.smallrye.mutiny.Uni;

public interface CreateMessageProducedClientMessageOperation {
    Uni<Boolean> execute(Long clientId,
                         Object message);
}
