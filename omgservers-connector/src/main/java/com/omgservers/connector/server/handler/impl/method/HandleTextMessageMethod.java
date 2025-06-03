package com.omgservers.connector.server.handler.impl.method;

import com.omgservers.connector.server.handler.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;

public interface HandleTextMessageMethod {
    Uni<Void> execute(HandleTextMessageRequest request);
}
