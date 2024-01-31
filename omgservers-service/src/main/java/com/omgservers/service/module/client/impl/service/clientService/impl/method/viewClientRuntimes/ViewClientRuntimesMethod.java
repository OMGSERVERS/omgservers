package com.omgservers.service.module.client.impl.service.clientService.impl.method.viewClientRuntimes;

import com.omgservers.model.dto.client.ViewClientRuntimesRequest;
import com.omgservers.model.dto.client.ViewClientRuntimesResponse;
import io.smallrye.mutiny.Uni;

public interface ViewClientRuntimesMethod {
    Uni<ViewClientRuntimesResponse> viewClientRuntimes(ViewClientRuntimesRequest request);
}
