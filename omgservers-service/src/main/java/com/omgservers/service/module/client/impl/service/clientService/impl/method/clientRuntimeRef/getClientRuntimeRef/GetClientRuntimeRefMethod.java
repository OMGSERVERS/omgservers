package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.getClientRuntimeRef;

import com.omgservers.model.dto.client.GetClientRuntimeRefRequest;
import com.omgservers.model.dto.client.GetClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface GetClientRuntimeRefMethod {
    Uni<GetClientRuntimeRefResponse> getClientRuntimeRef(GetClientRuntimeRefRequest request);
}
