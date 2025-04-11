package com.omgservers.service.operation.client;

import io.smallrye.mutiny.Uni;

import java.net.URI;

public interface CreateDispatcherConnectionUpgradedClientMessageOperation {
    Uni<Boolean> executeFailSafe(URI connectionUrl,
                                 Long clientId);
}
