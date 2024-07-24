package com.omgservers.service.service.room.impl.method.removeConnection;

import com.omgservers.service.service.room.dto.RemoveConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface RemoveConnectionMethod {
    /**
     * Just removing connection from the room.
     */
    Uni<Void> removeConnection(RemoveConnectionRequest request);
}
