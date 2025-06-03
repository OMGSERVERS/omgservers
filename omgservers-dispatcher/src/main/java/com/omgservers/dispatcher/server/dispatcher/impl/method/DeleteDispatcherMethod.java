package com.omgservers.dispatcher.server.dispatcher.impl.method;

import com.omgservers.dispatcher.server.dispatcher.dto.DeleteDispatcherRequest;
import com.omgservers.dispatcher.server.dispatcher.dto.DeleteDispatcherResponse;
import io.smallrye.mutiny.Uni;

public interface DeleteDispatcherMethod {

    Uni<DeleteDispatcherResponse> execute(DeleteDispatcherRequest request);
}
