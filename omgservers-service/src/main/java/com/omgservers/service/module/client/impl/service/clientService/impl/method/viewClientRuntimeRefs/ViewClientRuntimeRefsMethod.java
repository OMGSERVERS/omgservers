package com.omgservers.service.module.client.impl.service.clientService.impl.method.viewClientRuntimeRefs;

import com.omgservers.model.dto.client.ViewClientRuntimeRefsRequest;
import com.omgservers.model.dto.client.ViewClientRuntimeRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewClientRuntimeRefsMethod {
    Uni<ViewClientRuntimeRefsResponse> viewClientRuntimeRefs(ViewClientRuntimeRefsRequest request);
}
