package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.closeClientConnection;

import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionResponse;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface CloseClientConnectionMethod {

    Uni<CloseClientConnectionResponse> closeClientConnection(CloseClientConnectionRequest request);
}
