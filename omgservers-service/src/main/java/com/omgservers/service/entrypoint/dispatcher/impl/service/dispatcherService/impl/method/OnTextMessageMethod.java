package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.entrypoint.dispatcher.dto.OnTextMessageDispatcherRequest;
import io.smallrye.mutiny.Uni;

public interface OnTextMessageMethod {
    Uni<Void> execute(OnTextMessageDispatcherRequest request);
}
