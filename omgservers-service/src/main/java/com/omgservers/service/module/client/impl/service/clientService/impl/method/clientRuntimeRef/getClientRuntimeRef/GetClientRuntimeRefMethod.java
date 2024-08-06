package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.getClientRuntimeRef;

import com.omgservers.schema.module.client.GetClientRuntimeRefRequest;
import com.omgservers.schema.module.client.GetClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetClientRuntimeRefMethod {
    Uni<GetClientRuntimeRefResponse> getClientRuntimeRef(GetClientRuntimeRefRequest request);
}
