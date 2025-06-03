package com.omgservers.dispatcher.server.dispatcher.impl.method;

import com.omgservers.dispatcher.server.dispatcher.dto.AddPlayerConnectionRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.AddPlayerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface AddPlayerConnectionMethod {

    Uni<AddPlayerConnectionResponse> execute(AddPlayerConnectionRequest request);
}
