package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMatchmakerRef.getClientMatchmakerRef;

import com.omgservers.schema.module.client.GetClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.GetClientMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetClientMatchmakerRefMethod {
    Uni<GetClientMatchmakerRefResponse> getClientMatchmakerRef(GetClientMatchmakerRefRequest request);
}
