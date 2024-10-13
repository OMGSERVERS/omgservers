package com.omgservers.service.service.dispatcher.impl.method.handleBinaryMessage;

import com.omgservers.service.service.dispatcher.dto.HandleBinaryMessageRequest;
import io.smallrye.mutiny.Uni;

public interface HandleBinaryMessageMethod {
    Uni<Void> handleBinaryMessage(HandleBinaryMessageRequest request);
}
