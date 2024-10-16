package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleTextMessageRequest;
import io.smallrye.mutiny.Uni;

public interface HandleTextMessageMethod {
    Uni<Void> execute(HandleTextMessageRequest request);
}
