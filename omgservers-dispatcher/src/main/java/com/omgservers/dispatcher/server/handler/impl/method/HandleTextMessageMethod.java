package com.omgservers.dispatcher.server.handler.impl.method;

import com.omgservers.dispatcher.server.handler.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;

public interface HandleTextMessageMethod {
    Uni<Void> execute(HandleTextMessageRequest request);
}
