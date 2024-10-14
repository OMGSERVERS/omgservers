package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleOpenedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleOpenedConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface HandleOpenedConnectionMethod {
    Uni<HandleOpenedConnectionResponse> execute(HandleOpenedConnectionRequest request);
}
