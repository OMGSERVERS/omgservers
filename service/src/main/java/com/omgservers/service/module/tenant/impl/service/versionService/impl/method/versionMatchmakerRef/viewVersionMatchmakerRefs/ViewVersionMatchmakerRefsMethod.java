package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.viewVersionMatchmakerRefs;

import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRefsRequest;
import com.omgservers.schema.module.tenant.ViewVersionMatchmakerRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionMatchmakerRefsMethod {
    Uni<ViewVersionMatchmakerRefsResponse> viewVersionMatchmakerRefs(ViewVersionMatchmakerRefsRequest request);
}
