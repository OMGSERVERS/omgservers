package com.omgservers.service.module.tenant.impl.service.versionService.impl.method.viewVersionMatchmakers;

import com.omgservers.model.dto.tenant.ViewVersionMatchmakersRequest;
import com.omgservers.model.dto.tenant.ViewVersionMatchmakersResponse;
import io.smallrye.mutiny.Uni;

public interface ViewVersionMatchmakersMethod {
    Uni<ViewVersionMatchmakersResponse> viewVersionMatchmakers(ViewVersionMatchmakersRequest request);
}
