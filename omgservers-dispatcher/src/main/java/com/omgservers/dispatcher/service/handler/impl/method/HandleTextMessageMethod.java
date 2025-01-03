package com.omgservers.dispatcher.service.handler.impl.method;

import com.omgservers.dispatcher.service.handler.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;

public interface HandleTextMessageMethod {
    Uni<Void> execute(HandleTextMessageRequest request);
}
