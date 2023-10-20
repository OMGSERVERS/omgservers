package com.omgservers.module.tenant.impl.service.versionService.impl.method.viewVersionMatchmakers;

import com.omgservers.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.dto.tenant.ViewVersionMatchmakersResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionMatchmakersMethod {
    Uni<ViewVersionMatchmakersResponse> viewVersionMatchmakers(ViewVersionMatchmakersRequest request);
}
