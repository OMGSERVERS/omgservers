package com.omgservers.dispatcher.service.router.impl.method.closeClientConnection;

import com.omgservers.dispatcher.service.router.dto.CloseClientConnectionRequest;
import com.omgservers.dispatcher.service.router.dto.CloseClientConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface CloseClientConnectionMethod {

    Uni<CloseClientConnectionResponse> execute(CloseClientConnectionRequest request);
}
