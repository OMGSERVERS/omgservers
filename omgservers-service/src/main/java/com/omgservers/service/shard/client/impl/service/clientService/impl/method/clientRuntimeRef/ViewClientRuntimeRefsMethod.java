package com.omgservers.service.shard.client.impl.service.clientService.impl.method.clientRuntimeRef;

import com.omgservers.schema.module.client.clientRuntimeRef.ViewClientRuntimeRefsRequest;
import com.omgservers.schema.module.client.clientRuntimeRef.ViewClientRuntimeRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewClientRuntimeRefsMethod {
    Uni<ViewClientRuntimeRefsResponse> execute(ViewClientRuntimeRefsRequest request);
}
