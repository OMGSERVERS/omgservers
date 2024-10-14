package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.AddConnectionRequest;
import io.smallrye.mutiny.Uni;

public interface AddConnectionMethod {

    /**
     * Add a new connection to the room.
     * In case of reusing of tokenId, previous connection will be closed.
     */
    Uni<Void> execute(AddConnectionRequest request);
}
