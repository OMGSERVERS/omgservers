package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.module.client.clientRuntimeRef.FindClientRuntimeRefRequest;
import com.omgservers.schema.module.client.clientRuntimeRef.FindClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindClientRuntimeRefMethod {
    Uni<FindClientRuntimeRefResponse> execute(FindClientRuntimeRefRequest request);
}
