package com.omgservers.application.module.internalModule.impl.service.eventInternalService;

import com.omgservers.application.module.internalModule.impl.service.eventInternalService.request.FireEventInternalRequest;
import com.omgservers.application.module.internalModule.impl.service.eventInternalService.response.FireEventInternalResponse;
import io.smallrye.mutiny.Uni;

public interface EventInternalService {

    Uni<FireEventInternalResponse> fireEvent(FireEventInternalRequest request);
}
