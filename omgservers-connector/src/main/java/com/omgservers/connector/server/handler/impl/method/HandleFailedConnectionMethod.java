package com.omgservers.connector.server.handler.impl.method;

import com.omgservers.connector.server.handler.dto.HandleFailedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleFailedConnectionMethod {
    Uni<Void> execute(HandleFailedConnectionRequest request);
}
