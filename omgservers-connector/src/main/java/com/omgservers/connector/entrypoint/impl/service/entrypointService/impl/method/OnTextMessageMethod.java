package com.omgservers.connector.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.connector.entrypoint.impl.dto.OnTextMessageEntrypointRequest;
import io.smallrye.mutiny.Uni;

public interface OnTextMessageMethod {
    Uni<Void> execute(OnTextMessageEntrypointRequest request);
}
