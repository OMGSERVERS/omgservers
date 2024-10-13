package com.omgservers.service.service.dispatcher.impl.method.createRoom;

import com.omgservers.service.service.dispatcher.dto.CreateRoomRequest;
import com.omgservers.service.service.dispatcher.dto.CreateRoomResponse;
import io.smallrye.mutiny.Uni;

public interface CreateRoomMethod {
    /**
     * Create a new room for runtime if it doesn't exist
     */
    Uni<CreateRoomResponse> creteRoom(CreateRoomRequest request);
}
