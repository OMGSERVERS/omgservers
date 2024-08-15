package com.omgservers.service.service.router.impl.method.closeClientConnection;

import com.omgservers.service.service.router.dto.CloseClientConnectionRequest;
import com.omgservers.service.service.router.dto.CloseClientConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface CloseClientConnectionMethod {

    Uni<CloseClientConnectionResponse> closeClientConnection(CloseClientConnectionRequest request);
}
