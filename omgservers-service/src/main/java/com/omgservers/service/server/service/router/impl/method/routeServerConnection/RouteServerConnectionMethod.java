package com.omgservers.service.server.service.router.impl.method.routeServerConnection;

import com.omgservers.service.server.service.router.dto.RouteServerConnectionRequest;
import com.omgservers.service.server.service.router.dto.RouteServerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface RouteServerConnectionMethod {

    Uni<RouteServerConnectionResponse> routeServerConnection(RouteServerConnectionRequest request);
}
