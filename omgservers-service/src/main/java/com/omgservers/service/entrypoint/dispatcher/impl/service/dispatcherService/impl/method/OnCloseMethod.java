package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.entrypoint.dispatcher.dto.OnCloseDispatcherRequest;
import io.smallrye.mutiny.Uni;

public interface OnCloseMethod {
    Uni<Void> execute(OnCloseDispatcherRequest request);
}
