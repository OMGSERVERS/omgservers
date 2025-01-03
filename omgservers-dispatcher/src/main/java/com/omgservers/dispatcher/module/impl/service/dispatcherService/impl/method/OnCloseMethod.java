package com.omgservers.dispatcher.module.impl.service.dispatcherService.impl.method;

import com.omgservers.dispatcher.module.impl.dto.OnCloseRequest;
import io.smallrye.mutiny.Uni;

public interface OnCloseMethod {
    Uni<Void> execute(OnCloseRequest request);
}
