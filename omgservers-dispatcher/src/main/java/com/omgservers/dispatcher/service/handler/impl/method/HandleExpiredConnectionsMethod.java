package com.omgservers.dispatcher.service.handler.impl.method;

import com.omgservers.dispatcher.service.handler.dto.HandleExpiredConnectionsRequest;
import io.smallrye.mutiny.Uni;

public interface HandleExpiredConnectionsMethod {
    Uni<Void> execute(HandleExpiredConnectionsRequest request);
}
