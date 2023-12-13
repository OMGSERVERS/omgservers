package com.omgservers.service.module.runtime.impl.service.runtimeService.impl.method.viewRuntimeClients;

import com.omgservers.model.dto.runtime.ViewRuntimeClientsRequest;
import com.omgservers.model.dto.runtime.ViewRuntimeClientsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRuntimeClientsMethod {
    Uni<ViewRuntimeClientsResponse> viewRuntimeClients(ViewRuntimeClientsRequest request);
}
