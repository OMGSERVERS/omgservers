package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.service.dispatcher.dto.HandleOpenedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleOpenedConnectionMethod {
    Uni<Void> execute(HandleOpenedConnectionRequest request);
}
