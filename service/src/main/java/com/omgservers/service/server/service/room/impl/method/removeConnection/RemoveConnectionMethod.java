package com.omgservers.service.server.service.room.impl.method.removeConnection;

import com.omgservers.service.server.service.room.dto.RemoveConnectionRequest;
import com.omgservers.service.server.service.room.dto.RemoveConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface RemoveConnectionMethod {
    /**
     * Just removing connection from the room.
     */
    Uni<Void> removeConnection(RemoveConnectionRequest request);
}
