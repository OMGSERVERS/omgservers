package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.service.dispatcher.dto.HandleFailedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleFailedConnectionMethod {
    Uni<Void> execute(HandleFailedConnectionRequest request);
}
