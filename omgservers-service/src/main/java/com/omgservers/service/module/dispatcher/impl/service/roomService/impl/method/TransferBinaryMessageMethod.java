package com.omgservers.service.module.dispatcher.impl.service.roomService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.roomService.dto.HandleBinaryMessageRequest;
import io.smallrye.mutiny.Uni;

public interface TransferBinaryMessageMethod {
    Uni<Void> execute(HandleBinaryMessageRequest request);
}
