package com.omgservers.service.service.dispatcher.impl.method.removeConnection;

import com.omgservers.service.service.dispatcher.dto.RemoveConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface RemoveConnectionMethod {
    /**
     * Just removing connection from the room.
     */
    Uni<Void> removeConnection(RemoveConnectionRequest request);
}
