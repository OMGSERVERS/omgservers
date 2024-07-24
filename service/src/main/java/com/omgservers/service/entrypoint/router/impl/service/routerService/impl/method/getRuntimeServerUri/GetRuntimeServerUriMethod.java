package com.omgservers.service.entrypoint.router.impl.service.routerService.impl.method.getRuntimeServerUri;

import com.omgservers.model.dto.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.model.dto.router.GetRuntimeServerUriRouterResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeServerUriMethod {
    Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(GetRuntimeServerUriRouterRequest request);
}
