package com.omgservers.service.service.room.impl.method.createRoom;

import com.omgservers.service.service.room.dto.CreateRoomRequest;
import com.omgservers.service.service.room.dto.CreateRoomResponse;
import io.smallrye.mutiny.Uni;

public interface CreateRoomMethod {
    /**
     * Create a new room for runtime if it doesn't exist
     */
    Uni<CreateRoomResponse> creteRoom(CreateRoomRequest request);
}
