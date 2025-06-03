package com.omgservers.connector.server.handler.impl.method;

import com.omgservers.connector.server.handler.dto.HandleBinaryMessageRequest;
import io.smallrye.mutiny.Uni;

public interface HandleBinaryMessageMethod {
    Uni<Void> execute(HandleBinaryMessageRequest request);
}
