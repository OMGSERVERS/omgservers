package com.omgservers.connector.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.connector.entrypoint.impl.dto.OnErrorEntrypointRequest;
import io.smallrye.mutiny.Uni;

public interface OnErrorMethod {
    Uni<Void> execute(OnErrorEntrypointRequest request);
}
