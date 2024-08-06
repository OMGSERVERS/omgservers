package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientRuntimeRef.viewClientRuntimeRefs;

import com.omgservers.schema.module.client.ViewClientRuntimeRefsRequest;
import com.omgservers.schema.module.client.ViewClientRuntimeRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewClientRuntimeRefsMethod {
    Uni<ViewClientRuntimeRefsResponse> viewClientRuntimeRefs(ViewClientRuntimeRefsRequest request);
}
