package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientMatchmakerRef.findClientMatchmakerRef;

import com.omgservers.schema.module.client.FindClientMatchmakerRefRequest;
import com.omgservers.schema.module.client.FindClientMatchmakerRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindClientMatchmakerRefMethod {
    Uni<FindClientMatchmakerRefResponse> findClientMatchmakerRef(FindClientMatchmakerRefRequest request);
}
