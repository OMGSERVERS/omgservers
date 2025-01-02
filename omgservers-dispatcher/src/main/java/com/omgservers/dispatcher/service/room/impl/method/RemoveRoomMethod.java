package com.omgservers.dispatcher.service.room.impl.method;

import com.omgservers.dispatcher.service.room.dto.RemoveRoomRequest;
import com.omgservers.dispatcher.service.room.dto.RemoveRoomResponse;
import io.smallrye.mutiny.Uni;

public interface RemoveRoomMethod {

    Uni<RemoveRoomResponse> execute(RemoveRoomRequest request);
}
