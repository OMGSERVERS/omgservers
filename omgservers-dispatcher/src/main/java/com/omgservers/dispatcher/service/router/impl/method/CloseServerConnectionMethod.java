package com.omgservers.dispatcher.service.router.impl.method;

import com.omgservers.dispatcher.service.router.dto.CloseServerConnectionRequest;
import com.omgservers.dispatcher.service.router.dto.CloseServerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface CloseServerConnectionMethod {

    Uni<CloseServerConnectionResponse> execute(CloseServerConnectionRequest request);
}
