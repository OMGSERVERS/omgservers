package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleFailedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleFailedConnectionMethod {
    Uni<Void> execute(HandleFailedConnectionRequest request);
}
