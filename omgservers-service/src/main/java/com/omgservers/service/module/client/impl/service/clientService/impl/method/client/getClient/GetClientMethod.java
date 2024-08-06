package com.omgservers.service.module.client.impl.service.clientService.impl.method.client.getClient;

import com.omgservers.schema.module.client.GetClientRequest;
import com.omgservers.schema.module.client.GetClientResponse;
import io.smallrye.mutiny.Uni;

public interface GetClientMethod {
    Uni<GetClientResponse> getClient(GetClientRequest request);
}
