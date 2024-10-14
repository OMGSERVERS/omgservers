package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;

public interface TransferTextMessageMethod {
    Uni<Void> execute(HandleTextMessageRequest request);
}
