package com.omgservers.dispatcher.module.impl.service.dispatcherService.impl.method;

import com.omgservers.dispatcher.module.impl.dto.OnErrorRequest;
import io.smallrye.mutiny.Uni;

public interface OnErrorMethod {
    Uni<Void> execute(OnErrorRequest request);
}
