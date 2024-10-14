package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.closeServerConnection;

import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseServerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseServerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface CloseServerConnectionMethod {

    Uni<CloseServerConnectionResponse> closeServerConnection(CloseServerConnectionRequest request);
}
