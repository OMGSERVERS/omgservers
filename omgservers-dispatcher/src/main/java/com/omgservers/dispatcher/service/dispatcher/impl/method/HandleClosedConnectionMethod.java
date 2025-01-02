package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.service.dispatcher.dto.HandleClosedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleClosedConnectionMethod {
    Uni<Void> execute(HandleClosedConnectionRequest request);
}
