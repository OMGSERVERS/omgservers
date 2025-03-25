package com.omgservers.service.shard.client.impl.service.clientService.impl.method.client;

import com.omgservers.schema.module.client.client.GetClientRequest;
import com.omgservers.schema.module.client.client.GetClientResponse;
import io.smallrye.mutiny.Uni;

public interface GetClientMethod {
    Uni<GetClientResponse> execute(GetClientRequest request);
}
