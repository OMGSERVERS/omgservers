package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMessage.interchange;

import com.omgservers.model.dto.client.InterchangeRequest;
import com.omgservers.model.dto.client.InterchangeResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMethod {
    Uni<InterchangeResponse> interchange(InterchangeRequest request);
}
