package com.omgservers.connector.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.connector.entrypoint.impl.dto.OnOpenEntrypointRequest;
import io.smallrye.mutiny.Uni;

public interface OnOpenMethod {
    Uni<Void> execute(OnOpenEntrypointRequest request);
}
