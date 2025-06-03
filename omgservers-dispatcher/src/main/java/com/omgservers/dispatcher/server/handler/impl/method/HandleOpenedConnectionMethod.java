package com.omgservers.dispatcher.server.handler.impl.method;

import com.omgservers.dispatcher.server.handler.dto.HandleOpenedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleOpenedConnectionMethod {
    Uni<Void> execute(HandleOpenedConnectionRequest request);
}
