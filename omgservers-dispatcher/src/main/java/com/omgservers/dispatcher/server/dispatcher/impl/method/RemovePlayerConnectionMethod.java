package com.omgservers.dispatcher.server.dispatcher.impl.method;

import com.omgservers.dispatcher.server.dispatcher.dto.RemovePlayerConnectionRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.RemovePlayerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface RemovePlayerConnectionMethod {

    Uni<RemovePlayerConnectionResponse> execute(RemovePlayerConnectionRequest request);
}
