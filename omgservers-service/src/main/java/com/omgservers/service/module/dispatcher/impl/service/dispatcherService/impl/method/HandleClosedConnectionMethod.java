package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleClosedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleClosedConnectionMethod {
    Uni<Void> execute(HandleClosedConnectionRequest request);
}
