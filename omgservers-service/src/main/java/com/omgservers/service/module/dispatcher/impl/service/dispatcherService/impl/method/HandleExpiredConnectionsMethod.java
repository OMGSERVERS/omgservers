package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleExpiredConnectionsRequest;
import io.smallrye.mutiny.Uni;

public interface HandleExpiredConnectionsMethod {
    Uni<Void> execute(HandleExpiredConnectionsRequest request);
}
