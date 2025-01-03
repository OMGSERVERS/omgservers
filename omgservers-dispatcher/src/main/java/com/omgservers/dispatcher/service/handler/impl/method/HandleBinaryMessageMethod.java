package com.omgservers.dispatcher.service.handler.impl.method;

import com.omgservers.dispatcher.service.handler.dto.HandleBinaryMessageRequest;
import io.smallrye.mutiny.Uni;

public interface HandleBinaryMessageMethod {
    Uni<Void> execute(HandleBinaryMessageRequest request);
}
