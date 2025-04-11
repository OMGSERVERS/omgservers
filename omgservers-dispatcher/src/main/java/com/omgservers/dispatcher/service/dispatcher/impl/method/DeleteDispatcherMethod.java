package com.omgservers.dispatcher.service.dispatcher.impl.method;

import com.omgservers.dispatcher.service.dispatcher.dto.DeleteDispatcherRequest;
import com.omgservers.dispatcher.service.dispatcher.dto.DeleteDispatcherResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteDispatcherMethod {

    Uni<DeleteDispatcherResponse> execute(DeleteDispatcherRequest request);
}
