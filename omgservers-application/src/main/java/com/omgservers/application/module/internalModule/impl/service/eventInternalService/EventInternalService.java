package com.omgservers.application.module.internalModule.impl.service.eventInternalService;

import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import io.smallrye.mutiny.Uni;

public interface EventInternalService {

    Uni<Void> fireEvent(FireEventInternalRequest request);
}
