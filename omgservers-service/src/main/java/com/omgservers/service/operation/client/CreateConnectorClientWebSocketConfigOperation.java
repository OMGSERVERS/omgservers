package com.omgservers.service.operation.client;

import com.omgservers.schema.security.WebSocketConfig;

public interface CreateConnectorClientWebSocketConfigOperation {
    WebSocketConfig execute(Long clientId);
}
