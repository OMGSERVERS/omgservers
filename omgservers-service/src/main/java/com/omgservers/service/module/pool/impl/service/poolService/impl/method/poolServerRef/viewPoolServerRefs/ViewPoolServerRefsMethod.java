package com.omgservers.service.module.pool.impl.service.poolService.impl.method.poolServerRef.viewPoolServerRefs;

import com.omgservers.model.dto.pool.ViewPoolServerRefsRequest;
import com.omgservers.model.dto.pool.ViewPoolServerRefsResponse;
import io.smallrye.mutiny.Uni;

public interface ViewPoolServerRefsMethod {
    Uni<ViewPoolServerRefsResponse> viewPoolServerRefs(ViewPoolServerRefsRequest request);
}
