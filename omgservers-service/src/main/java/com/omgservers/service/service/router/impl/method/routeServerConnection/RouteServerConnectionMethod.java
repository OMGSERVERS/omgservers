package com.omgservers.service.service.router.impl.method.routeServerConnection;

import com.omgservers.service.service.router.dto.RouteServerConnectionRequest;
import com.omgservers.service.service.router.dto.RouteServerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface RouteServerConnectionMethod {

    Uni<RouteServerConnectionResponse> routeServerConnection(RouteServerConnectionRequest request);
}
