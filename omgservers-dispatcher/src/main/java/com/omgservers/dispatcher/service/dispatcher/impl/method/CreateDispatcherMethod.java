package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.service.dispatcher.dto.CreateDispatcherRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.CreateDispatcherResponse;
import io.smallrye.mutiny.Uni;

public interface CreateDispatcherMethod {

    Uni<CreateDispatcherResponse> execute(CreateDispatcherRequest request);
}
