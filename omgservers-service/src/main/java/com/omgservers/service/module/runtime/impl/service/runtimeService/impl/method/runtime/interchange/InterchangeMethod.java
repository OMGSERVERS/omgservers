package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.runtime.interchange;

import com.omgservers.schema.module.runtime.InterchangeRequest;
import com.omgservers.schema.module.runtime.InterchangeResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMethod {
    Uni<InterchangeResponse> interchange(InterchangeRequest request);
}
