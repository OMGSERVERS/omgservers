package com.omgservers.dispatcher.service.room.impl.method;

import com.omgservers.dispatcher.service.room.dto.CreateRoomRequest;
import com.omgservers.dispatcher.service.room.dto.CreateRoomResponse;
import io.smallrye.mutiny.Uni;

public interface CreateRoomMethod {

    Uni<CreateRoomResponse> execute(CreateRoomRequest request);
}
