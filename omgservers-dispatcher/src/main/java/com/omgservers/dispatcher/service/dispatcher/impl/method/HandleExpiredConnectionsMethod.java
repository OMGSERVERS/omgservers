package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.service.dispatcher.dto.HandleExpiredConnectionsRequest;
import io.smallrye.mutiny.Uni;

public interface HandleExpiredConnectionsMethod {
    Uni<Void> execute(HandleExpiredConnectionsRequest request);
}
