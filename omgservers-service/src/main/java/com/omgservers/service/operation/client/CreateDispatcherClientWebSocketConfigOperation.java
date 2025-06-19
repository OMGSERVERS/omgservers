package com.omgservers.service.operation.client;

import com.omgservers.schema.security.WebSocketConfig;

public interface CreateDispatcherClientWebSocketConfigOperation {
    WebSocketConfig execute(Long clientId, Long runtimeId);
}
