package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMessage.interchange;

import com.omgservers.schema.module.client.InterchangeRequest;
import com.omgservers.schema.module.client.InterchangeResponse;
import io.smallrye.mutiny.Uni;

public interface InterchangeMethod {
    Uni<InterchangeResponse> interchange(InterchangeRequest request);
}
