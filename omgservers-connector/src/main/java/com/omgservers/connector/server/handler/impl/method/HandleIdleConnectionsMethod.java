package com.omgservers.connector.server.handler.impl.method;

import com.omgservers.connector.server.handler.dto.HandleIdleConnectionsRequest;
import io.smallrye.mutiny.Uni;

public interface HandleIdleConnectionsMethod {
    Uni<Void> execute(HandleIdleConnectionsRequest request);
}
