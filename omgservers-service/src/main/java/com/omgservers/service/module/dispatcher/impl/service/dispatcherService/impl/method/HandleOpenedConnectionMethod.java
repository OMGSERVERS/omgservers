package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleOpenedConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface HandleOpenedConnectionMethod {
    Uni<Void> execute(HandleOpenedConnectionRequest request);
}
