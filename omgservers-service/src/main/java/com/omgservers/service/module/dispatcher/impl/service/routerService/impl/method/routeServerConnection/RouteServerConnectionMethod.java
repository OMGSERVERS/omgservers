package com.omgservers.service.module.dispatcher.impl.service.routerService.impl.method.routeServerConnection;

import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.routerService.dto.RouteServerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface RouteServerConnectionMethod {

    Uni<RouteServerConnectionResponse> routeServerConnection(RouteServerConnectionRequest request);
}
