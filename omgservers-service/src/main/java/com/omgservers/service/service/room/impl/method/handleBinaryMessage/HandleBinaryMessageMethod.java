package com.omgservers.service.service.room.impl.method.handleBinaryMessage;

import com.omgservers.service.service.room.dto.HandleBinaryMessageRequest;
import io.smallrye.mutiny.Uni;

public interface HandleBinaryMessageMethod {
    Uni<Void> handleBinaryMessage(HandleBinaryMessageRequest request);
}
