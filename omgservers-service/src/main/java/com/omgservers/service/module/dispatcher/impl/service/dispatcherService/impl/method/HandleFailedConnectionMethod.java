package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleFailedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleFailedConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface HandleFailedConnectionMethod {
    Uni<HandleFailedConnectionResponse> execute(HandleFailedConnectionRequest request);
}
