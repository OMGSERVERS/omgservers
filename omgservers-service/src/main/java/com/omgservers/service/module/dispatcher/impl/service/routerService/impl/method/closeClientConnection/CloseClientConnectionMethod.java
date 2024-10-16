package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.closeClientConnection;

import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.CloseClientConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface CloseClientConnectionMethod {

    Uni<CloseClientConnectionResponse> execute(CloseClientConnectionRequest request);
}
