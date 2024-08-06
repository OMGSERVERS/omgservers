package com.omgservers.service.module.client.impl.service.clientService.impl.method.clientMatchmakerRef.viewClientMatchmakerRefs;

import com.omgservers.schema.module.client.ViewClientMatchmakerRefsRequest;
import com.omgservers.schema.module.client.ViewClientMatchmakerRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewClientMatchmakerRefsMethod {
    Uni<ViewClientMatchmakerRefsResponse> viewClientMatchmakerRefs(ViewClientMatchmakerRefsRequest request);
}
