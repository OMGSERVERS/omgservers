package com.omgservers.application.module.internalModule.impl.service.eventInternalService.impl.method.fireEventMethod;

import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import io.smallrye.mutiny.Uni;

public interface FireEventMethod {
    Uni<Void> fireEvent(FireEventInternalRequest request);
}
