package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.CreateRoomRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.CreateRoomResponse;
import io.smallrye.mutiny.Uni;

public interface CreateRoomMethod {

    Uni<CreateRoomResponse> execute(CreateRoomRequest request);
}
