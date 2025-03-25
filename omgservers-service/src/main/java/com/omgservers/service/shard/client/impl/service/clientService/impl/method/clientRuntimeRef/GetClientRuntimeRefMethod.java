package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.module.client.clientRuntimeRef.GetClientRuntimeRefRequest;
import com.omgservers.schema.module.client.clientRuntimeRef.GetClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetClientRuntimeRefMethod {
    Uni<GetClientRuntimeRefResponse> execute(GetClientRuntimeRefRequest request);
}
