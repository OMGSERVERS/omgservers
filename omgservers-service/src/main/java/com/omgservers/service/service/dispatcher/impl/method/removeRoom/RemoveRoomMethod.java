package com.omgservers.service.service.dispatcher.impl.method.removeRoom;

import com.omgservers.service.service.dispatcher.dto.RemoveRoomRequest;
import com.omgservers.service.service.dispatcher.dto.RemoveRoomResponse;
import io.smallrye.mutiny.Uni;

public interface RemoveRoomMethod {
    /**
     * Remove room from container and close all room connections.
     */
    Uni<RemoveRoomResponse> removeRoom(RemoveRoomRequest request);
}
