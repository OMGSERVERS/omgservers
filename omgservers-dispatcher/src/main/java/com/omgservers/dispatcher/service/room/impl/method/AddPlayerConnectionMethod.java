package com.omgservers.dispatcher.service.room.impl.method;

import com.omgservers.dispatcher.service.room.dto.AddPlayerConnectionRequest;
import com.omgservers.dispatcher.service.room.dto.AddPlayerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface AddPlayerConnectionMethod {

    Uni<AddPlayerConnectionResponse> execute(AddPlayerConnectionRequest request);
}
