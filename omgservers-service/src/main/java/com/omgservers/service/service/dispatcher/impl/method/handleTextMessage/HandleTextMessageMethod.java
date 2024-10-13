package com.omgservers.service.service.dispatcher.impl.method.handleTextMessage;

import com.omgservers.service.service.dispatcher.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;

public interface HandleTextMessageMethod {
    Uni<Void> handleTextMessage(HandleTextMessageRequest request);
}
