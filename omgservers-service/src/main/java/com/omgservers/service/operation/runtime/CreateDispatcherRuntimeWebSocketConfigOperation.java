package com.omgservers.service.operation.runtime;

import com.omgservers.schema.security.WebSocketConfig;

public interface CreateDispatcherRuntimeWebSocketConfigOperation {
    WebSocketConfig execute(Long userId, Long runtimeId);
}
