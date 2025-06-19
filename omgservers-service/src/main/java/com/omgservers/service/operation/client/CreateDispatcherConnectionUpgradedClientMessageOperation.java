package com.omgservers.service.operation.client;

import com.omgservers.schema.security.WebSocketConfig;
import io.smallrye.mutiny.Uni;

public interface CreateDispatcherConnectionUpgradedClientMessageOperation {
    Uni<Boolean> executeFailSafe(WebSocketConfig webSocketConfig,
                                 Long clientId);
}
