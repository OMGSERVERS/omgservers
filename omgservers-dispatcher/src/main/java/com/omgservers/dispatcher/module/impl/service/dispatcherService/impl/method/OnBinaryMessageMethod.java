package com.omgservers.dispatcher.module.impl.service.dispatcherService.impl.method;

import com.omgservers.dispatcher.module.impl.dto.OnBinaryMessageRequest;
import io.smallrye.mutiny.Uni;

public interface OnBinaryMessageMethod {
    Uni<Void> execute(OnBinaryMessageRequest request);
}
