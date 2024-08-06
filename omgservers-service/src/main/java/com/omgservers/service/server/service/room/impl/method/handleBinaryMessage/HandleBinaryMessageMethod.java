package com.omgservers.service.server.service.room.impl.method.handleBinaryMessage;

import com.omgservers.service.server.service.room.dto.HandleBinaryMessageRequest;
import com.omgservers.service.server.service.room.dto.HandleBinaryMessageRequest;
import io.smallrye.mutiny.Uni;

public interface HandleBinaryMessageMethod {
    Uni<Void> handleBinaryMessage(HandleBinaryMessageRequest request);
}
