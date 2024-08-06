package com.omgservers.service.module.root.impl.service.rootService.impl.method.rootEntityRef.viewRootEntityRefs;

import com.omgservers.schema.module.root.rootEntityRef.ViewRootEntityRefsRequest;
import com.omgservers.schema.module.root.rootEntityRef.ViewRootEntityRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewRootEntityRefsMethod {
    Uni<ViewRootEntityRefsResponse> viewRootEntityRefs(ViewRootEntityRefsRequest request);
}
