package com.omgservers.dispatcher.server.handler.impl.method;

import com.omgservers.dispatcher.server.handler.dto.HandleClosedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleClosedConnectionMethod {
    Uni<Void> execute(HandleClosedConnectionRequest request);
}
