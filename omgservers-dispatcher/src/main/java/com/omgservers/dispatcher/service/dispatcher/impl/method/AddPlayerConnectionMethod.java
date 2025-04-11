package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.service.dispatcher.dto.AddPlayerConnectionRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.AddPlayerConnectionResponse;
import io.smallrye.mutiny.Uni;

public interface AddPlayerConnectionMethod {

    Uni<AddPlayerConnectionResponse> execute(AddPlayerConnectionRequest request);
}
