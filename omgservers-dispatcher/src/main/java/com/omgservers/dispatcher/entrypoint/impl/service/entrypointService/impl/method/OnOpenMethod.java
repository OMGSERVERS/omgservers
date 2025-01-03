package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.impl.dto.OnOpenEntrypointRequest;
import io.smallrye.mutiny.Uni;

public interface OnOpenMethod {
    Uni<Void> execute(OnOpenEntrypointRequest request);
}
