package com.omgservers.connector.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.connector.entrypoint.impl.dto.OnBinaryMessageEntrypointRequest;
import io.smallrye.mutiny.Uni;

public interface OnBinaryMessageMethod {
    Uni<Void> execute(OnBinaryMessageEntrypointRequest request);
}
