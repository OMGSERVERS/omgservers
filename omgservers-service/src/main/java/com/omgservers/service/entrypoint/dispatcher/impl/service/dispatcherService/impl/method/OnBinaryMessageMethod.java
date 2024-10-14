package com.omgservers.service.entrypoint.dispatcher.impl.service.dispatcherService.impl.method;

import com.omgservers.service.entrypoint.dispatcher.dto.OnBinaryMessageDispatcherRequest;
import io.smallrye.mutiny.Uni;

public interface OnBinaryMessageMethod {
    Uni<Void> execute(OnBinaryMessageDispatcherRequest request);
}
