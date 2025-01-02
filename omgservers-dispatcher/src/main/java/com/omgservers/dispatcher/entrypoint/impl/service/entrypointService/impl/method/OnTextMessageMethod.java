package com.omgservers.dispatcher.entrypoint.impl.service.entrypointService.impl.method;

import com.omgservers.dispatcher.entrypoint.dto.OnTextMessageEntrypointRequest;
import io.smallrye.mutiny.Uni;

public interface OnTextMessageMethod {
    Uni<Void> execute(OnTextMessageEntrypointRequest request);
}
