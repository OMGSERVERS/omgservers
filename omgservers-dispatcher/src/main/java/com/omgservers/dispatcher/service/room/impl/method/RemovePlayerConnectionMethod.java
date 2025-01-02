package com.omgservers.dispatcher.service.room.impl.method;

import com.omgservers.dispatcher.service.room.dto.RemovePlayerConnectionRequest;
import com.omgservers.dispatcher.service.room.dto.RemovePlayerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface RemovePlayerConnectionMethod {

    Uni<RemovePlayerConnectionResponse> execute(RemovePlayerConnectionRequest request);
}
