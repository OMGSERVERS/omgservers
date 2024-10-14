package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveRoomResponse;
import io.smallrye.mutiny.Uni;

public interface RemoveRoomMethod {
    /**
     * Remove room from container and close all room connections.
     */
    Uni<RemoveRoomResponse> execute(RemoveRoomRequest request);
}
