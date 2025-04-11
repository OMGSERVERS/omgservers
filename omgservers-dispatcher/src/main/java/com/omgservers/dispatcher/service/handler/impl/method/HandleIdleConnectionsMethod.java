package com.omgservers.dispatcher.service.handler.impl.method;

import com.omgservers.dispatcher.service.handler.dto.HandleIdleConnectionsRequest;
import io.smallrye.mutiny.Uni;

public interface HandleIdleConnectionsMethod {
    Uni<Void> execute(HandleIdleConnectionsRequest request);
}
