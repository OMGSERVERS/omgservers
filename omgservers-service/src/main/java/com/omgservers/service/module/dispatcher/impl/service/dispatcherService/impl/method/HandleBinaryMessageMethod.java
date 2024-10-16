package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleBinaryMessageRequest;
import io.smallrye.mutiny.Uni;

public interface HandleBinaryMessageMethod {
    Uni<Void> execute(HandleBinaryMessageRequest request);
}
