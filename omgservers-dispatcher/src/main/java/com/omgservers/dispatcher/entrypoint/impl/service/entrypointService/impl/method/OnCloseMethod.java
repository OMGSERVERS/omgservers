package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.impl.dto.OnCloseEntrypointRequest;
import io.smallrye.mutiny.Uni;

public interface OnCloseMethod {
    Uni<Void> execute(OnCloseEntrypointRequest request);
}
