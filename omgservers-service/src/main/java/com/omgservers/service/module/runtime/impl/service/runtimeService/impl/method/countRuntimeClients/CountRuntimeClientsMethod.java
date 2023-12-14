package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.countRuntimeClients;

import com.omgservers.model.dto.runtime.CountRuntimeClientsRequest;
import com.omgservers.model.dto.runtime.CountRuntimeClientsResponse;
import io.smallrye.mutiny.Uni;

public interface CountRuntimeClientsMethod {
    Uni<CountRuntimeClientsResponse> countRuntimeClients(CountRuntimeClientsRequest request);
}
