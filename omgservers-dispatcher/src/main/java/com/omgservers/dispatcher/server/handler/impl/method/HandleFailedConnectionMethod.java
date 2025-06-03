package com.omgservers.dispatcher.server.handler.impl.method;

import com.omgservers.dispatcher.server.handler.dto.HandleFailedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleFailedConnectionMethod {
    Uni<Void> execute(HandleFailedConnectionRequest request);
}
