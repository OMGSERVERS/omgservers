package com.omgservers.base.impl.service.eventInternalService;

import com.omgservers.dto.internalModule.FireEventInternalRequest;
import com.omgservers.dto.internalModule.FireEventInternalResponse;
import io.smallrye.mutiny.Uni;

public interface EventInternalService {

    Uni<FireEventInternalResponse> fireEvent(FireEventInternalRequest request);
}
