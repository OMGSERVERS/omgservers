package com.omgservers.connector.server.handler.impl.method;

import com.omgservers.connector.server.handler.dto.HandleClosedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleClosedConnectionMethod {
    Uni<Void> execute(HandleClosedConnectionRequest request);
}
