package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemovePlayerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.RemovePlayerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface RemovePlayerConnectionMethod {

    Uni<RemovePlayerConnectionResponse> execute(RemovePlayerConnectionRequest request);
}
