package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.AddPlayerConnectionRequest;
import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.AddPlayerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface AddPlayerConnectionMethod {

    Uni<AddPlayerConnectionResponse> execute(AddPlayerConnectionRequest request);
}
