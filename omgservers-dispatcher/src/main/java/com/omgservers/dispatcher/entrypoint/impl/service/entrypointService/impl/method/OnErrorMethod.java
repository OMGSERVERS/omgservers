package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.impl.dto.OnErrorEntrypointRequest;
import io.smallrye.mutiny.Uni;

public interface OnErrorMethod {
    Uni<Void> execute(OnErrorEntrypointRequest request);
}
