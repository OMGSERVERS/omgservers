package com.omgservers.dispatcher.service.handler.impl.method;

import com.omgservers.dispatcher.service.handler.dto.HandleOpenedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleOpenedConnectionMethod {
    Uni<Void> execute(HandleOpenedConnectionRequest request);
}
