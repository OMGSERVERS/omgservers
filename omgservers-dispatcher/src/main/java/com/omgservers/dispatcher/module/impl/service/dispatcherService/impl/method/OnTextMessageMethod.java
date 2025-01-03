package com.omgservers.dispatcher.module.impl.service.dispatcherService.impl.method;

import com.omgservers.dispatcher.module.impl.dto.OnTextMessageRequest;
import io.smallrye.mutiny.Uni;

public interface OnTextMessageMethod {
    Uni<Void> execute(OnTextMessageRequest request);
}
