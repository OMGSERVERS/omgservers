package com.omgservers.dispatcher.service.router.impl.method;

import com.omgservers.dispatcher.service.router.dto.RouteServerConnectionRequest;
import com.omgservers.dispatcher.service.router.dto.RouteServerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface RouteServerConnectionMethod {

    Uni<RouteServerConnectionResponse> execute(RouteServerConnectionRequest request);
}
