package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.findClientRuntimeRef;

import com.omgservers.model.dto.client.FindClientRuntimeRefRequest;
import com.omgservers.model.dto.client.FindClientRuntimeRefResponse;
import io.smallrye.mutiny.Uni;

public interface FindClientRuntimeRefMethod {
    Uni<FindClientRuntimeRefResponse> findClientRuntimeRef(FindClientRuntimeRefRequest request);
}
