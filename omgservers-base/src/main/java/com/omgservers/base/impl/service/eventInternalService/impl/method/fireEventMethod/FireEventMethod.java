package com.omgservers.base.impl.service.eventInternalService.impl.method.fireEventMethod;

import com.omgservers.dto.internalModule.FireEventInternalRequest;
import com.omgservers.dto.internalModule.FireEventInternalResponse;
import io.smallrye.mutiny.Uni;

public interface FireEventMethod {
    Uni<FireEventInternalResponse> fireEvent(FireEventInternalRequest request);
}
