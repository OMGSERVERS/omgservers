package com.omgservers.dispatcher.server.handler.impl.method;

import com.omgservers.dispatcher.server.handler.dto.HandleIdleConnectionsRequest;
import io.smallrye.mutiny.Uni;

public interface HandleIdleConnectionsMethod {
    Uni<Void> execute(HandleIdleConnectionsRequest request);
}
