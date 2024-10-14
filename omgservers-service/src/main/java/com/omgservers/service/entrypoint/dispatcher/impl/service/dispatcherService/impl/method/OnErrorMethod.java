package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.entrypoint.dispatcher.dto.OnErrorDispatcherRequest;
import io.smallrye.mutiny.Uni;

public interface OnErrorMethod {
    Uni<Void> execute(OnErrorDispatcherRequest request);
}
