package com.omgservers.service.module.client.impl.service.clientService.impl.method.findClientRuntime;

import com.omgservers.model.dto.client.FindClientRuntimeRequest;
import com.omgservers.model.dto.client.FindClientRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface FindClientRuntimeMethod {
    Uni<FindClientRuntimeResponse> findClientRuntime(FindClientRuntimeRequest request);
}
