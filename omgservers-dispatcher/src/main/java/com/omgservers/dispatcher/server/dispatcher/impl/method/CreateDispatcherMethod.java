package com.omgservers.dispatcher.server.dispatcher.impl.method;

import com.omgservers.dispatcher.server.dispatcher.dto.CreateDispatcherRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.CreateDispatcherResponse;
import io.smallrye.mutiny.Uni;

public interface CreateDispatcherMethod {

    Uni<CreateDispatcherResponse> execute(CreateDispatcherRequest request);
}
