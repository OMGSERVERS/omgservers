package com.omgservers.dispatcher.module.impl.service.dispatcherService.impl.method;

import com.omgservers.dispatcher.module.impl.dto.OnOpenRequest;
import io.smallrye.mutiny.Uni;

public interface OnOpenMethod {
    Uni<Void> execute(OnOpenRequest request);
}
