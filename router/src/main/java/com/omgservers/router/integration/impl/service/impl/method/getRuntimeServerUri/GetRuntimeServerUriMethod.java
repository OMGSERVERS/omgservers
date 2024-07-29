package com.omgservers.router.integration.impl.service.impl.method.getRuntimeServerUri;

import com.omgservers.schema.entrypoint.router.GetRuntimeServerUriRouterRequest;
import com.omgservers.schema.entrypoint.router.GetRuntimeServerUriRouterResponse;
import io.smallrye.mutiny.Uni;

public interface GetRuntimeServerUriMethod {
    Uni<GetRuntimeServerUriRouterResponse> getRuntimeServerUri(GetRuntimeServerUriRouterRequest request);
}



