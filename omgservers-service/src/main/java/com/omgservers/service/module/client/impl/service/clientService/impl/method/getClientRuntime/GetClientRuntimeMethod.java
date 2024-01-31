package com.omgservers.service.module.client.impl.service.clientService.impl.method.getClientRuntime;

import com.omgservers.model.dto.client.GetClientRuntimeRequest;
import com.omgservers.model.dto.client.GetClientRuntimeResponse;
import io.smallrye.mutiny.Uni;

public interface GetClientRuntimeMethod {
    Uni<GetClientRuntimeResponse> getClientRuntime(GetClientRuntimeRequest request);
}
