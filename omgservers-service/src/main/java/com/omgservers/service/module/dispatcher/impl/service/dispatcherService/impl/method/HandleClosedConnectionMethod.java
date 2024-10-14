package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleClosedConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleClosedConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface HandleClosedConnectionMethod {
    Uni<HandleClosedConnectionResponse> execute(HandleClosedConnectionRequest request);
}
