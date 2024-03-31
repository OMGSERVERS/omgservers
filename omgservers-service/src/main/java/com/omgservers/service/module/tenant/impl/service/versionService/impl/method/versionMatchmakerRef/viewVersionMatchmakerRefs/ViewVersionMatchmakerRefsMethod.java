package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.versionMatchmakerRef.viewVersionMatchmakerRefs;

import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRefsRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakerRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionMatchmakerRefsMethod {
    Uni<ViewVersionMatchmakerRefsResponse> viewVersionMatchmakerRefs(ViewVersionMatchmakerRefsRequest request);
}
