package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleTextMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleTextMessageResponse;
import io.smallrye.mutiny.Uni;

public interface HandleTextMessageMethod {
    Uni<HandleTextMessageResponse> execute(HandleTextMessageRequest request);
}
