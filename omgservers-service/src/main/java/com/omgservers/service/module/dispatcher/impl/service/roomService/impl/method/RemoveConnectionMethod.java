package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemoveConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface RemoveConnectionMethod {
    /**
     * Just removing connection from the room.
     */
    Uni<Void> execute(RemoveConnectionRequest request);
}
