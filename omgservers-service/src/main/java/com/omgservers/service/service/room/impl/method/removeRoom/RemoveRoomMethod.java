package com.omgservers.service.service.room.impl.method.removeRoom;

import com.omgservers.service.service.room.dto.RemoveRoomRequest;
import com.omgservers.service.service.room.dto.RemoveRoomResponse;
import io.smallrye.mutiny.Uni;

public interface RemoveRoomMethod {
    /**
     * Remove room from container and close all room connections.
     */
    Uni<RemoveRoomResponse> removeRoom(RemoveRoomRequest request);
}
