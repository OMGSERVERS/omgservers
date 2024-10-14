package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.entrypoint.dispatcher.dto.OnOpenDispatcherRequest;
import io.smallrye.mutiny.Uni;

public interface OnOpenMethod {
    Uni<Void> execute(OnOpenDispatcherRequest request);
}
