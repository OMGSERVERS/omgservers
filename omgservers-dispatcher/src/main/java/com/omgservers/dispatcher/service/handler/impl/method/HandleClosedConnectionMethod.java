package com.omgservers.dispatcher.service.handler.impl.method;

import com.omgservers.dispatcher.service.handler.dto.HandleClosedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleClosedConnectionMethod {
    Uni<Void> execute(HandleClosedConnectionRequest request);
}
