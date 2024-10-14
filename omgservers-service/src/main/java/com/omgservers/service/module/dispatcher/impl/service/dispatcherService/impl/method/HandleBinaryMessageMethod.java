package com.omgservers.service.module.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleBinaryMessageRequest;
import com.omgservers.service.module.dispatcher.impl.service.dispatcherService.dto.HandleBinaryMessageResponse;
import io.smallrye.mutiny.Uni;

public interface HandleBinaryMessageMethod {
    Uni<HandleBinaryMessageResponse> execute(HandleBinaryMessageRequest request);
}
