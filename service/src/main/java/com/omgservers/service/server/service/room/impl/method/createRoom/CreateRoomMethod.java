package com.omgservers.service.server.service.room.impl.method.createRoom;

import com.omgservers.service.server.service.room.dto.CreateRoomRequest;
import io.smallrye.mutiny.Uni;

public interface CreateRoomMethod {
    /**
     * Create a new room for runtime if it doesn't exist
     */
    Uni<Void> creteRoom(CreateRoomRequest request);
}
