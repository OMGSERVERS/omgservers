package com.omgservers.service.server.service.room.impl.method.handleTextMessage;

import com.omgservers.service.server.service.room.dto.HandleTextMessageRequest;
import com.omgservers.service.server.service.room.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;

public interface HandleTextMessageMethod {
    Uni<Void> handleTextMessage(HandleTextMessageRequest request);
}
