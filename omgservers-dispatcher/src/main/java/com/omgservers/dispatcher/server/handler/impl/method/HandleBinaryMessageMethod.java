package com.omgservers.dispatcher.server.handler.impl.method;

import com.omgservers.dispatcher.server.handler.dto.HandleBinaryMessageRequest;
import io.smallrye.mutiny.Uni;

public interface HandleBinaryMessageMethod {
    Uni<Void> execute(HandleBinaryMessageRequest request);
}
