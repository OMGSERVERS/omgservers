package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.interchange;

import com.omgservers.model.dto.runtime.InterchangeRequest;
import com.omgservers.model.dto.runtime.InterchangeResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMethod {
    Uni<InterchangeResponse> interchange(InterchangeRequest request);
}
