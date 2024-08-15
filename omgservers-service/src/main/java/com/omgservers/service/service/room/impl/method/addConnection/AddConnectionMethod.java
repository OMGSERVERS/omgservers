package com.omgservers.service.service.room.impl.method.addConnection;

import com.omgservers.service.service.room.dto.AddConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface AddConnectionMethod {

    /**
     * Add a new connection to the room.
     * In case of reusing of tokenId, previous connection will be closed.
     */
    Uni<Void> addConnection(AddConnectionRequest request);
}
