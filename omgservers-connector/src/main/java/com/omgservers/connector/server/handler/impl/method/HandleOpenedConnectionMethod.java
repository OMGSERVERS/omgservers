package com.omgservers.connector.server.handler.impl.method;

import com.omgservers.connector.server.handler.dto.HandleOpenedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleOpenedConnectionMethod {
    Uni<Void> execute(HandleOpenedConnectionRequest request);
}
