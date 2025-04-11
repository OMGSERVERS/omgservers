package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.service.dispatcher.dto.RemovePlayerConnectionRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.RemovePlayerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface RemovePlayerConnectionMethod {

    Uni<RemovePlayerConnectionResponse> execute(RemovePlayerConnectionRequest request);
}
