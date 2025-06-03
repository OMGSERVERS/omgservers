package com.omgservers.connector.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.connector.entrypoint.impl.dto.OnCloseEntrypointRequest;
import io.smallrye.mutiny.Uni;

public interface OnCloseMethod {
    Uni<Void> execute(OnCloseEntrypointRequest request);
}
