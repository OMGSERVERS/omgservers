package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.impl.dto.OnBinaryMessageEntrypointRequest;
import io.smallrye.mutiny.Uni;

public interface OnBinaryMessageMethod {
    Uni<Void> execute(OnBinaryMessageEntrypointRequest request);
}
