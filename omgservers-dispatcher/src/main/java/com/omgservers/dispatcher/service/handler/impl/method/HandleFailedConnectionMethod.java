package com.omgservers.dispatcher.service.handler.impl.method;

import com.omgservers.dispatcher.service.handler.dto.HandleFailedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleFailedConnectionMethod {
    Uni<Void> execute(HandleFailedConnectionRequest request);
}
